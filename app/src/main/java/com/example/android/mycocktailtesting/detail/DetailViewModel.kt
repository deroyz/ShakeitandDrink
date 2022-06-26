package com.example.android.mycocktailtesting.detail

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.android.mycocktailtesting.domain.Drink

class DetailViewModel(drink: Drink, application: Application): AndroidViewModel(application){

    private val _selectedDrink = MutableLiveData<Drink>()
    val selectedDrink: LiveData<Drink>
        get() = _selectedDrink

    init{
        _selectedDrink.value = drink
    }
}

class DetailViewModelFactory(private val drink: Drink, private val app: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            Log.e("ViewModelFactory", "DetailViewModel Assigned")
            return DetailViewModel(drink, app) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}