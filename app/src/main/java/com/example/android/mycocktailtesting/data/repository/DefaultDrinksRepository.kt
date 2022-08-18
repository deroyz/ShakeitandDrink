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

class DefaultDrinksRepository
@Inject
constructor(
    private val drinkDatabase: DrinkDatabase,
    private val cocktailDbAPI: CocktailDbAPI
) : DrinksRepository {

    override fun getFilterList(): List<String> {
        return CocktailDatabaseFilter.values().map { it.value }
    }

    override fun    getAllRandomDrinks(): LiveData<List<Drink>> {
        return Transformations.map(drinkDatabase.drinkDao.getAllRandomDrinks()) {
            it.asDomainModelRandomDrink()
        }
    }

    override fun getAllPopularDrinks(): LiveData<List<Drink>> {
        return Transformations.map(drinkDatabase.drinkDao.getAllPopularDrinks()) {
            it.asDomainModelPopularDrink()
        }
    }

    override fun getAllLatestDrinks(): LiveData<List<Drink>> {
        return Transformations.map(drinkDatabase.drinkDao.getAllLatestDrinks()) {
            it.asDomainModelLatestDrink()
        }
    }


    override fun getAllFavoriteDrinks(): LiveData<List<Drink>> {
        return Transformations.map(drinkDatabase.drinkDao.getAllFavoriteDrinks()) {
            it.asDomainModelFavoriteDrink()
        }
    }

    override suspend fun refreshRandomDrinks() {

        withContext(Dispatchers.IO) {
            val randomCocktails = cocktailDbAPI.getRandomCocktails().await()
            drinkDatabase.drinkDao.insertAllRandomDrinks(*randomCocktails.asDatabaseModelRandomDrink())
        }
    }

    override suspend fun refreshPopularDrinks() {
        withContext(Dispatchers.IO) {
            val popularCocktails = cocktailDbAPI.getPopularCocktails().await()
            drinkDatabase.drinkDao.insertAllPopularDrinks(*popularCocktails.asDatabaseModelPopularDrink())
        }
    }

    override suspend fun refreshLatestDrinks() {
        withContext(Dispatchers.IO) {
            val latestCocktails = cocktailDbAPI.getLatestCocktails().await()
            drinkDatabase.drinkDao.insertAllLatestDrinks(*latestCocktails.asDatabaseModelLatestDrink())
        }
    }

    // Favorite Check, Insert, Delete
    override suspend fun checkIsFavorite(idDrink: Double): Boolean {
        return withContext(Dispatchers.IO) {
            drinkDatabase.drinkDao.checkFavoriteById(idDrink)
        }
    }

    override suspend fun insertFavoriteDrink(drink: Drink) {
        withContext(Dispatchers.IO) {
            drinkDatabase.drinkDao.insertFavoriteDrink(drink.asDatabaseModelFavoriteDrink())
        }
    }

    override suspend fun deleteFavoriteDrink(drink: Drink) {
        withContext(Dispatchers.IO) {
            drinkDatabase.drinkDao.deleteFavoriteDrinkById(drink.asDatabaseModelFavoriteDrink().idDrink)
        }
    }

}

