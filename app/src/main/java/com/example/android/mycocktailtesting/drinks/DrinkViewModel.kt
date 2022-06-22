package com.example.android.mycocktailtesting.drinks

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android.mycocktailtesting.network.Drink

class DrinkViewModel: ViewModel() {

    enum class CocktailApiStatus{LOADING, ERROR, DONE}

    // The internal MutableLiveData String that stores the status of the most recent request
    private val _status = MutableLiveData<CocktailApiStatus>()

    // The external immutable LiveData for the request status String
    val status: LiveData<CocktailApiStatus>
        get() = _status

    private val _drinks = MutableLiveData<List<Drink>>()
    val drinks: LiveData<List<Drink>>
        get() = _drinks

    init{
        getDrinks()
    }

    private fun getDrinks() {
corouineScope.launch{

}
    }

}