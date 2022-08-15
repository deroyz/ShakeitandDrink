package com.example.android.mycocktailtesting.drink.drinks

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

    var randomDrinkList = MutableLiveData<List<Drink>>()
    var popularDrinkList = MutableLiveData<List<Drink>>()
    var latestDrink = MutableLiveData<List<Drink>>()
    var favoriteDrink = MutableLiveData<List<Drink>>()

    init {
        Log.e("DrinkViewModel", "DrinkViewModel Init")

        viewModelScope.launch {
            defaultDrinksRepository.refreshRandomDrinks()
            defaultDrinksRepository.refreshPopularDrinks()
            defaultDrinksRepository.refreshLatestDrinks()
        }
        getDrinksLists(defaultDrinksRepository)
        filter.value = CocktailDatabaseFilter.SHOW_TODAYS
        filterList.value = defaultDrinksRepository.getFilterList()
    }

    private fun getDrinksLists(defaultDrinksRepository: DefaultDrinksRepository) {
        randomDrinkList =
            defaultDrinksRepository.getAllRandomDrinks() as MutableLiveData<List<Drink>>
        popularDrinkList =
            defaultDrinksRepository.getAllPopularDrinks() as MutableLiveData<List<Drink>>
        latestDrink =
            defaultDrinksRepository.getAllLatestDrinks() as MutableLiveData<List<Drink>>
        favoriteDrink =
            defaultDrinksRepository.getAllFavoriteDrinks() as MutableLiveData<List<Drink>>
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
