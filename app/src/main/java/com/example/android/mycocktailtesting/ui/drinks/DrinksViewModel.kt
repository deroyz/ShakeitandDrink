package com.example.android.mycocktailtesting.ui.drinks

import android.util.Log
import androidx.lifecycle.*
import com.example.android.mycocktailtesting.data.database.CocktailDatabaseFilter
import com.example.android.mycocktailtesting.data.domain.Drink
import com.example.android.mycocktailtesting.data.repository.DefaultDrinksRepository
import com.example.android.mycocktailtesting.data.repository.DrinksRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DrinksViewModel @Inject constructor(drinksRepository: DrinksRepository) :
    ViewModel() {

    enum class CocktailApiStatus { LOADING, ERROR, DONE }

    val filterList = MutableLiveData<List<String>>()
    val filter = MutableLiveData<CocktailDatabaseFilter>()
    val navigateToSelectedDrink = MutableLiveData<Drink>()

    var randomDrinkList = drinksRepository.getAllRandomDrinks()
    var popularDrinkList = drinksRepository.getAllPopularDrinks()
    var latestDrink = drinksRepository.getAllLatestDrinks()
    var favoriteDrink =  drinksRepository.getAllFavoriteDrinks()

    init {
        Log.e("DrinkViewModel", "DrinkViewModel Init")

        viewModelScope.launch {
            drinksRepository.refreshRandomDrinks()
            drinksRepository.refreshPopularDrinks()
            drinksRepository.refreshLatestDrinks()
        }
        filter.value = CocktailDatabaseFilter.SHOW_TODAYS
        filterList.value = drinksRepository.getFilterList()
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
