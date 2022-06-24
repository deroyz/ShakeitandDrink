package com.example.android.mycocktailtesting.drinks

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.mycocktailtesting.database.getDatabase

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
//
//    private val _drinks = MutableLiveData<List<NetworkDrink>>()
//    val drinks: LiveData<List<NetworkDrink>>
//        get() = _drinks

    private val database = getDatabase(application)
    private val drinksRepository = DrinksRepository(database)

    init {
        Log.e("DrinkViewModel", "init1")

        viewModelScope.launch {
            drinksRepository.refreshRandomDrinks()
            Log.e("DrinkViewModel", "init2")

        }
    }

    val drinkList = drinksRepository.drinks

}
