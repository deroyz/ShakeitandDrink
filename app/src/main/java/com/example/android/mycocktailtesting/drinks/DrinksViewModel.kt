package com.example.android.mycocktailtesting.drinks

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.android.mycocktailtesting.database.CocktailDatabaseFilter
import com.example.android.mycocktailtesting.database.getDatabase
import com.example.android.mycocktailtesting.domain.Drink
import com.example.android.mycocktailtesting.repository.DrinksRepository
import kotlinx.coroutines.launch

class DrinksViewModel(application: Application) : AndroidViewModel(application) {

    enum class CocktailApiStatus { LOADING, ERROR, DONE }

    //    // The internal MutableLiveData String that stores the status of the most recent request
//    private val _status = MutableLiveData<CocktailApiStatus>()
//
//    // The external immutable LiveData for the request status String
//    val status: LiveData<CocktailApiStatus>
//        get() = _status

    private val database = getDatabase(application)
    private val drinksRepository = DrinksRepository(database)

    val filterList = MutableLiveData<List<String>>(    )
    val filter = MutableLiveData<CocktailDatabaseFilter>()
    val navigateToSelectedDrink = MutableLiveData<Drink>()

    val randomDrinkList = drinksRepository.randomDrinks
    val popularDrinkList = drinksRepository.popularDrinks
    val latestDrink = drinksRepository.latestDrinks
    val favoriteDrink = drinksRepository.favoriteDrinks


    init {
        Log.e("DrinkViewModel", "DrinkViewModel Init")

        viewModelScope.launch {
            drinksRepository.refreshRandomDrinks()
            drinksRepository.refreshPopularDrinks()
            drinksRepository.refreshLatestDrinks()
        }
        filter.value = CocktailDatabaseFilter.SHOW_TODAYS
        filterList.value = drinksRepository.filterList
    }

    fun updateFilter(filter: CocktailDatabaseFilter) {
        Log.e("ViewModel", "updateFilter to $filter")
        this.filter.value = filter
    }

    fun navigateToSelectedDrinkComplete() {
        navigateToSelectedDrink.value = null
    }

    fun navigateToSelectedDrink(drink: Drink) {
        navigateToSelectedDrink.value = drink
    }

}

class DrinksViewModelFactory(private val app: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DrinksViewModel::class.java)) {
            Log.e("ViewModelFactory", "DrinkViewModel Assigned")
            return DrinksViewModel(app) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
