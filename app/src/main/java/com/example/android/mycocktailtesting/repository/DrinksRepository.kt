package com.example.android.mycocktailtesting.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.android.mycocktailtesting.database.*
import com.example.android.mycocktailtesting.domain.Drink
import com.example.android.mycocktailtesting.network.Network
import com.example.android.mycocktailtesting.network.asDatabaseModelPopularDrink
import com.example.android.mycocktailtesting.network.asDatabaseModelRandomDrink
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DrinksRepository(val database: DrinkDatabase) {

    val randomDrinks: LiveData<List<Drink>> =
        Transformations.map(database.drinkDao.getRandomDrinks()) {
            it.asDomainModelRandomDrink()
        }

    val popularDrinks: LiveData<List<Drink>> =
        Transformations.map(database.drinkDao.getPopularDrinks()) {
            it.asDomainModelPopularDrink()
        }

    var drinks: LiveData<List<Drink>> = randomDrinks

    fun updateDrinks(filter: CocktailDatabaseFilter) {
        drinks = when (filter) {
            CocktailDatabaseFilter.SHOW_TODAYS -> randomDrinks
            CocktailDatabaseFilter.SHOW_POPULAR -> popularDrinks
            CocktailDatabaseFilter.SHOW_FAVORITE -> popularDrinks
        }
    }

    suspend fun refreshRandomDrinks() {
        withContext(Dispatchers.IO) {
            val randomCocktails = Network.cocktailDBService.getRandomCocktails().await()
            database.drinkDao.insertAllRandomDrinks(*randomCocktails.asDatabaseModelRandomDrink())
        }
    }

    suspend fun refreshPopularDrinks() {
        withContext(Dispatchers.IO) {
            val popularCocktails = Network.cocktailDBService.getPopularCocktails().await()
            database.drinkDao.insertAllPopularDrinks(*popularCocktails.asDatabaseModelPopularDrink())
        }
    }
}