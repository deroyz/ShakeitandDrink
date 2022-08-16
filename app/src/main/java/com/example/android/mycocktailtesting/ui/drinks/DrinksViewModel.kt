package com.example.android.mycocktailtesting.ui.drinks

import android.util.Log
import androidx.lifecycle.*
import com.example.android.mycocktailtesting.data.database.CocktailDatabaseFilter
import com.example.android.mycocktailtesting.data.domain.Drink
import com.example.android.mycocktailtesting.data.repository.DefaultDrinksRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DrinksViewModel @Inject constructor(defaultDrinksRepository: DefaultDrinksRepository) :
    ViewModel() {

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

    var randomDrinkList = defaultDrinksRepository.getAllRandomDrinks()
    var popularDrinkList = defaultDrinksRepository.getAllPopularDrinks()
    var latestDrink = defaultDrinksRepository.getAllLatestDrinks()
    var favoriteDrink =  defaultDrinksRepository.getAllFavoriteDrinks()

    init {
        Log.e("DrinkViewModel", "DrinkViewModel Init")

        viewModelScope.launch {
            defaultDrinksRepository.refreshRandomDrinks()
            defaultDrinksRepository.refreshPopularDrinks()
            defaultDrinksRepository.refreshLatestDrinks()
        }
        filter.value = CocktailDatabaseFilter.SHOW_TODAYS
        filterList.value = defaultDrinksRepository.getFilterList()
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
