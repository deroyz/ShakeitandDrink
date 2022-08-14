package com.example.android.mycocktailtesting.drink.detail

import android.util.Log
import androidx.lifecycle.*
import com.example.android.mycocktailtesting.data.domain.Drink
import com.example.android.mycocktailtesting.data.repository.DrinksRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val drinksRepository: DrinksRepository,
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
            isFavorite.value = drinksRepository.checkIsFavorite(drink.idDrink)
            println("${isFavorite.value}")
        }
    }

    fun favoriteStatusUpdate() {
        isFavorite.value = isFavorite.value != true

        selectedDrink.value?.let {
            if (isFavorite.value == true) {
                favoriteBtnActive(it)
            } else {
                favoriteBtnInactive(it.idDrink)
            }
        }
    }

    private fun favoriteBtnActive(drink: Drink) {
        Log.e("DetailViewModel", "favoriteBtnActive")
        viewModelScope.launch {
            drinksRepository.insertFavoriteDrink(drink)
        }
    }

    private fun favoriteBtnInactive(idDrink: Double) {
        Log.e("DetailViewModel", "favoriteBtnInactive")
        viewModelScope.launch {
            drinksRepository.deleteFavoriteDrink(idDrink)
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