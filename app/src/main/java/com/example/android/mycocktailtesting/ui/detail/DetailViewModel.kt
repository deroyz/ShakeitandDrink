package com.example.android.mycocktailtesting.ui.detail

import android.util.Log
import androidx.lifecycle.*
import com.example.android.mycocktailtesting.data.domain.Drink
import com.example.android.mycocktailtesting.data.repository.DefaultDrinksRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val defaultDrinksRepository: DefaultDrinksRepository,
    private val state: SavedStateHandle
) :
    ViewModel() {

    val drink = state.get<Drink>("selectedDrink")

    val selectedDrink = MutableLiveData<Drink>()
    val isFavorite = MutableLiveData<Boolean>()

    init {
        Log.e("DetailViewModel", "DetailViewModel Init")
        selectedDrink.value = drink
        drink?.let { initializeFavorite(it) }
    }

    private fun initializeFavorite(drink: Drink) {
        viewModelScope.launch {
            isFavorite.value = defaultDrinksRepository.checkIsFavorite(drink.idDrink)
            println("${isFavorite.value}")
        }
    }

    fun favoriteStatusUpdate() {
        isFavorite.value = isFavorite.value != true

        selectedDrink.value?.let {
            if (isFavorite.value == true) {
                favoriteBtnActive(it)
            } else {
                favoriteBtnInactive(it)
            }
        }
    }

    private fun favoriteBtnActive(drink: Drink) {
        Log.e("DetailViewModel", "favoriteBtnActive")
        viewModelScope.launch {
            defaultDrinksRepository.insertFavoriteDrink(drink)
        }
    }

    private fun favoriteBtnInactive(drink: Drink) {
        Log.e("DetailViewModel", "favoriteBtnInactive")
        viewModelScope.launch {
            defaultDrinksRepository.deleteFavoriteDrink(drink)
            Log.e("DetailViewModel", "Selected ID deleted from the favorite table")
        }
    }
}

//class DetailViewModelFactory(private val drink: Drink) :
//    ViewModelProvider.Factory {
//    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
//            Log.e("ViewModelFactory", "DetailViewModel Assigned")
//            return DetailViewModel(drink) as T
//        }
//        throw IllegalArgumentException("Unknown ViewModel class")
//    }
//}