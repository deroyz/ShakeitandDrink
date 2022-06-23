package com.example.android.mycocktailtesting.repository

import DrinkDatabase
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import asDomainModel
import com.example.android.mycocktailtesting.domain.Drink
import com.example.android.mycocktailtesting.network.Network
import com.example.android.mycocktailtesting.network.asDatabaseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DrinksRepository(private val database: DrinkDatabase) {
    val drinks: LiveData<List<Drink>> = Transformations.map(database.drinkDao.getDrinks()) {
        it.asDomainModel()
    }

    suspend fun refreshDrinks() {
        withContext(Dispatchers.IO) {
            val randomCocktails = Network.cocktailDBService.getRandomCocktails().await()
            database.drinkDao.insertAll(*randomCocktails.asDatabaseModel())
        }
    }
}