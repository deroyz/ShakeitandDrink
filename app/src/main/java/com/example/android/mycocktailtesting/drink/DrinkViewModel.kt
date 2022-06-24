package com.example.android.mycocktailtesting.drink

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.android.mycocktailtesting.database.CocktailDatabaseFilter
import com.example.android.mycocktailtesting.database.getDatabase
import com.example.android.mycocktailtesting.domain.Drink
import com.example.android.mycocktailtesting.repository.DrinksRepository
import kotlinx.coroutines.launch

class DrinkViewModel(application: Application) : AndroidViewModel(application) {

    enum class CocktailApiStatus { LOADING, ERROR, DONE }


    //    // The internal MutableLiveData String that stores the status of the most recent request
//    private val _status = MutableLiveData<CocktailApiStatus>()
//
//    // The external immutable LiveData for the request status String
//    val status: LiveData<CocktailApiStatus>
//        get() = _status


    private val database = getDatabase(application)
    private val drinksRepository = DrinksRepository(database)

    private val _filter = MutableLiveData<CocktailDatabaseFilter>()
    val filter: LiveData<CocktailDatabaseFilter>
        get() = _filter


    var drinkList: LiveData<List<Drink>> = drinksRepository.randomDrinks

    init {
        Log.e("DrinkViewModel", "ViewModel Init")

        viewModelScope.launch {
            drinksRepository.refreshRandomDrinks()
            drinksRepository.refreshPopularDrinks()
        }
    }


    fun updateFilter(filter: CocktailDatabaseFilter) {
        Log.e("ViewModel", "updateFilter to $filter")
        _filter.value = filter
    }

    fun updateDrinkList() {
        Log.e("ViewModel", "updateDrinkList filter value is ${filter.value}")
        drinkList = when (_filter.value) {
            CocktailDatabaseFilter.SHOW_TODAYS -> drinksRepository.randomDrinks
            CocktailDatabaseFilter.SHOW_POPULAR -> drinksRepository.popularDrinks
            else -> drinksRepository.popularDrinks
        }
        Log.e("ViewModel", "Updated DrinkList is ${drinkList.value?.get(1)?.strDrink}")
    }
}
