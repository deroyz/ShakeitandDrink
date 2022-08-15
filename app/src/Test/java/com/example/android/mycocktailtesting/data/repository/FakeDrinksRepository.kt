package com.example.android.mycocktailtesting.data.repository

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.example.android.mycocktailtesting.data.database.*
import com.example.android.mycocktailtesting.data.domain.Drink
import com.example.android.mycocktailtesting.data.domain.asDatabaseModelFavoriteDrink
import com.example.android.mycocktailtesting.data.network.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FakeDrinksRepository : DrinksRepository {

    private val drinksListFromApi = listOf<NetworkDrink>()

    private val randomDrinks = mutableListOf<DatabaseRandomDrink>()
    private val allRandomDrinks = MutableLiveData<List<DatabaseRandomDrink>>(randomDrinks)

    private val latestDrinks = mutableListOf<DatabaseLatestDrink>()
    private val allLatestDrinks = MutableLiveData<List<DatabaseLatestDrink>>(latestDrinks)

    private val popularDrinks = mutableListOf<DatabasePopularDrink>()
    private val allPopularDrinks = MutableLiveData<List<DatabasePopularDrink>>(popularDrinks)

    private val favoriteDrinks = mutableListOf<DatabaseFavoriteDrink>()
    private val allFavoriteDrinks = MutableLiveData<List<DatabaseFavoriteDrink>>(favoriteDrinks)

    private var isFavorite = true

    private var shouldReturnNetworkError = false

    fun setShouldReturnNetworkError(value: Boolean) {
        shouldReturnNetworkError = value
    }

    fun setIsFavorite(value: Boolean) {
        isFavorite = value
    }

    private fun refreshLiveData() {
        allFavoriteDrinks.postValue(favoriteDrinks)
        allPopularDrinks.postValue(popularDrinks)
        allLatestDrinks.postValue(latestDrinks)
        allFavoriteDrinks.postValue(favoriteDrinks)
    }

    override fun getFilterList(): List<String> {
        return CocktailDatabaseFilter.values().map { it.value }
    }

    override fun getAllRandomDrinks(): LiveData<List<Drink>> {
        return Transformations.map(allRandomDrinks) { it.asDomainModelRandomDrink() }
    }

    override fun getAllPopularDrinks(): LiveData<List<Drink>> {
        return Transformations.map(allPopularDrinks) { it.asDomainModelPopularDrink() }
    }

    override fun getAllLatestDrinks(): LiveData<List<Drink>> {
        return Transformations.map(allLatestDrinks) { it.asDomainModelLatestDrink() }
    }

    override fun getAllFavoriteDrinks(): LiveData<List<Drink>> {
        return Transformations.map(allRandomDrinks) { it.asDomainModelRandomDrink() }
    }

    override suspend fun refreshRandomDrinks() {
        val randomCocktails = NetworkDrinkContainer(drinksListFromApi)
        randomDrinks.addAll(randomCocktails.asDatabaseModelRandomDrink())
        refreshLiveData()
    }

    override suspend fun refreshPopularDrinks() {
        val popularCocktails = NetworkDrinkContainer(drinksListFromApi)
        popularDrinks.addAll(popularCocktails.asDatabaseModelPopularDrink())
        refreshLiveData()
    }

    override suspend fun refreshLatestDrinks() {
        val latestCocktails = NetworkDrinkContainer(drinksListFromApi)
        latestDrinks.addAll(latestCocktails.asDatabaseModelLatestDrink())
        refreshLiveData()
    }

    override suspend fun checkIsFavorite(idDrink: Double): Boolean {
        return isFavorite
    }

    override suspend fun insertFavoriteDrink(drink: Drink) {
        favoriteDrinks.add(drink.asDatabaseModelFavoriteDrink())
        refreshLiveData()
    }

    override suspend fun deleteFavoriteDrink(drink: Drink) {
        favoriteDrinks.remove(drink.asDatabaseModelFavoriteDrink())
        refreshLiveData()
    }
}