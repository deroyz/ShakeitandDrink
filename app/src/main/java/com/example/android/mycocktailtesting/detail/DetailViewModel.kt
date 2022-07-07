package com.example.android.mycocktailtesting.detail

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.android.mycocktailtesting.R
import com.example.android.mycocktailtesting.database.getDatabase
import com.example.android.mycocktailtesting.databinding.FragmentDetailBinding
import com.example.android.mycocktailtesting.domain.Drink
import com.example.android.mycocktailtesting.repository.DrinksRepository
import kotlinx.coroutines.launch

class DetailViewModel(drink: Drink, application: Application) : AndroidViewModel(application) {

    private val database = getDatabase(application)
    private val drinksRepository = DrinksRepository(database)

    val selectedDrink = MutableLiveData<Drink>()
    val isFavorite = MutableLiveData<Boolean>()

    init {
        Log.e("DetailViewModel", "DetailViewModel Init")
        selectedDrink.value = drink
        initializeFavorite(drink)
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

class DetailViewModelFactory(private val drink: Drink, private val app: Application) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            Log.e("ViewModelFactory", "DetailViewModel Assigned")
            return DetailViewModel(drink, app) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}