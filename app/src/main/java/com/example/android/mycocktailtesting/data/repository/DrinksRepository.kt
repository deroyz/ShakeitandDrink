package com.example.android.mycocktailtesting.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.android.mycocktailtesting.data.database.*
import com.example.android.mycocktailtesting.data.domain.Drink
import com.example.android.mycocktailtesting.data.domain.asDatabaseModelFavoriteDrink
import com.example.android.mycocktailtesting.data.network.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DrinksRepository
@Inject
constructor(
    private val database: DrinkDatabase,
    private val cocktailDBService: CocktailDBService
) {

    val filterList: List<String> = CocktailDatabaseFilter.values().map { it.value }

    val randomDrinks: LiveData<List<Drink>> =
        Transformations.map(database.drinkDao.getAllRandomDrinks()) {
            it.asDomainModelRandomDrink()
        }

    val popularDrinks: LiveData<List<Drink>> =
        Transformations.map(database.drinkDao.getAllPopularDrinks()) {
            it.asDomainModelPopularDrink()
        }

    val latestDrinks: LiveData<List<Drink>> =
        Transformations.map(database.drinkDao.getAllLatestDrinks()) {
            it.asDomainModelLatestDrink()
        }

    val favoriteDrinks: LiveData<List<Drink>> =
        Transformations.map(database.drinkDao.getFavoriteDrinks()) {
            it.asDomainModelFavoriteDrink()
        }

    suspend fun refreshRandomDrinks() {
        withContext(Dispatchers.IO) {
            val randomCocktails = cocktailDBService.getRandomCocktails().await()
            database.drinkDao.insertAllRandomDrinks(*randomCocktails.asDatabaseModelRandomDrink())
        }
    }

    suspend fun refreshPopularDrinks() {
        withContext(Dispatchers.IO) {
            val popularCocktails = cocktailDBService.getPopularCocktails().await()
            database.drinkDao.insertAllPopularDrinks(*popularCocktails.asDatabaseModelPopularDrink())
        }
    }

    suspend fun refreshLatestDrinks() {
        withContext(Dispatchers.IO) {
            val latestCocktails = cocktailDBService.getLatestCocktails().await()
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

