package com.example.android.mycocktailtesting.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.android.mycocktailtesting.database.*
import com.example.android.mycocktailtesting.domain.Drink
import com.example.android.mycocktailtesting.domain.asDatabaseModelFavoriteDrink
import com.example.android.mycocktailtesting.network.Network
import com.example.android.mycocktailtesting.network.asDatabaseModelLatestDrink
import com.example.android.mycocktailtesting.network.asDatabaseModelPopularDrink
import com.example.android.mycocktailtesting.network.asDatabaseModelRandomDrink
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DrinksRepository(val database: DrinkDatabase) {

    val filterList: List<String> = CocktailDatabaseFilter.values().map{ it.value }

    val randomDrinks: LiveData<List<Drink>> =
        Transformations.map(database.drinkDao.getRandomDrinks()) {
            it.asDomainModelRandomDrink()
        }

    val popularDrinks: LiveData<List<Drink>> =
        Transformations.map(database.drinkDao.getPopularDrinks()) {
            it.asDomainModelPopularDrink()
        }

    val latestDrinks: LiveData<List<Drink>> =
        Transformations.map(database.drinkDao.getLatestDrinks()) {
            it.asDomainModelLatestDrink()
        }

    val favoriteDrinks: LiveData<List<Drink>> =
        Transformations.map(database.drinkDao.getFavoriteDrinks()) {
            it.asDomainModelFavoriteDrink()
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

    suspend fun refreshLatestDrinks() {
        withContext(Dispatchers.IO) {
            val latestCocktails = Network.cocktailDBService.getLatestCocktails().await()
            database.drinkDao.insertAllLatestDrinks(*latestCocktails.asDatabaseModelLatestDrink())
        }
    }

    // Favorite Check, Insert, Delete
    suspend fun checkIsFavorite(idDrink: Double): Boolean {
        return withContext(Dispatchers.IO) {
            database.drinkDao.checkFavoriteById(idDrink)
        }
    }

        suspend fun insertFavoriteDrink(drink: Drink) {
        withContext(Dispatchers.IO) {
            database.drinkDao.insertFavoriteDrink(drink.asDatabaseModelFavoriteDrink())
        }
    }

    suspend fun deleteFavoriteDrink(idDrink: Double) {
        withContext(Dispatchers.IO) {
            database.drinkDao.deleteFavoriteDrink(idDrink)
        }
    }
}

