package com.example.android.mycocktailtesting.drink.drinks

import android.util.Log
import androidx.lifecycle.*
import com.example.android.mycocktailtesting.model.database.CocktailDatabaseFilter
import com.example.android.mycocktailtesting.model.domain.Drink
import com.example.android.mycocktailtesting.model.repository.DrinksRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DrinksViewModel @Inject constructor(drinksRepository: DrinksRepository) : ViewModel() {

    enum class CocktailApiStatus { LOADING, ERROR, DONE }

    //    // The internal MutableLiveData String that stores the status of the most recent request
//    private val _status = MutableLiveData<CocktailApiStatus>()
//
//    // The external immutable LiveData for the request status String
//    val status: LiveData<CocktailApiStatus>
//        get() = _status

//    var drinksRepository = DrinksRepository(database)

    val filterList = MutableLiveData<List<String>>()
    val filter = MutableLiveData<CocktailDatabaseFilter>()
    val navigateToSelectedDrink = MutableLiveData<Drink>()

    var randomDrinkList = MutableLiveData<List<Drink>>()

//    val randomDrinkList = drinksRepository.randomDrinks
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
        randomDrinkList = drinksRepository.randomDrinks as MutableLiveData<List<Drink>>
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

//class DrinksViewModelFactory(private val app: Application) : ViewModelProvider.Factory {
//    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        if (modelClass.isAssignableFrom(DrinksViewModel::class.java)) {
//            Log.e("ViewModelFactory", "DrinkViewModel Assigned")
//            return DrinksViewModel(app) as T
//        }
//        throw IllegalArgumentException("Unknown ViewModel class")
//    }
//}
