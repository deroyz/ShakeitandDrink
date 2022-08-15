package com.example.android.mycocktailtesting.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.android.mycocktailtesting.data.database.*
import com.example.android.mycocktailtesting.data.domain.Drink
import com.example.android.mycocktailtesting.data.domain.asDatabaseModelFavoriteDrink
import com.example.android.mycocktailtesting.data.network.asDatabaseModelLatestDrink
import com.example.android.mycocktailtesting.data.network.asDatabaseModelPopularDrink
import com.example.android.mycocktailtesting.data.network.asDatabaseModelRandomDrink
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

interface DrinksRepository {

    fun getFilterList(): List<String>

    fun getAllRandomDrinks(): LiveData<List<Drink>>

    fun getAllPopularDrinks(): LiveData<List<Drink>>

    fun getAllLatestDrinks(): LiveData<List<Drink>>

    fun getAllFavoriteDrinks(): LiveData<List<Drink>>

    suspend fun refreshRandomDrinks()

    suspend fun refreshPopularDrinks()

    suspend fun refreshLatestDrinks()

    suspend fun checkIsFavorite(idDrink: Double): Boolean

    suspend fun insertFavoriteDrink(drink: Drink)

    suspend fun deleteFavoriteDrink(drink: Drink)

}