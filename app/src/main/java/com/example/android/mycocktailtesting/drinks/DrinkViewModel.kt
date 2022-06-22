package com.example.android.mycocktailtesting.drinks

import android.app.Application
import androidx.lifecycle.*
import com.example.android.devbyteviewer.database.getDatabase
import com.example.android.mycocktailtesting.network.NetworkDrink
import com.example.android.mycocktailtesting.repository.DrinksRepository
import kotlinx.coroutines.launch

class DevByteViewModel(application: Application) : AndroidViewModel(application) {

    enum class CocktailApiStatus { LOADING, ERROR, DONE }

    // The internal MutableLiveData String that stores the status of the most recent request
    private val _status = MutableLiveData<CocktailApiStatus>()

    // The external immutable LiveData for the request status String
    val status: LiveData<CocktailApiStatus>
        get() = _status

    private val _drinks = MutableLiveData<List<NetworkDrink>>()
    val drinks: LiveData<List<NetworkDrink>>
        get() = _drinks

    private val database = getDatabase(application)
    private val videosRepository = DrinksRepository(database)

    init {
        viewModelScope.launch {
            videosRepository.refreshDrinks()
        }
    }
}

class DrinkViewModelFactory(val app: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DrinkViewModelFactory::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DrinkViewModelFactory(app) as T
        }
        throw IllegalArgumentException("Unable to construct viewmodel")
    }
}
