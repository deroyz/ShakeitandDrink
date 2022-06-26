package com.example.android.mycocktailtesting.drink

import android.app.Application
import android.util.Log
import android.widget.Toast
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

    init {
        Log.e("DrinkViewModel", "ViewModel Init")

        viewModelScope.launch {
            drinksRepository.refreshRandomDrinks()
            drinksRepository.refreshPopularDrinks()
            _filter.value = CocktailDatabaseFilter.SHOW_TODAYS
        }
    }

    val randomDrinkList = drinksRepository.randomDrinks
    val popularDrinkList = drinksRepository.popularDrinks
    val favoriteDrink = drinksRepository.favoriteDrinks

    fun updateFilter(filter: CocktailDatabaseFilter) {
        Log.e("ViewModel", "updateFilter to $filter")
        _filter.value = filter
    }

    fun showToastMsg(drink: Drink) {
        Toast.makeText(this.getApplication(), drink.strDrink.toString(), Toast.LENGTH_SHORT).show()
    }
}
