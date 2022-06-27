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

    private val _selectedDrink = MutableLiveData<Drink>()
    val selectedDrink: LiveData<Drink>
        get() = _selectedDrink

    private val _isFavorite = MutableLiveData<Boolean>()
    val isFavorite: LiveData<Boolean>
        get() = _isFavorite


    init {
        _selectedDrink.value = drink
        viewModelScope.launch {
            _isFavorite.value = checkFavorite(drink).value
            Log.e("DetailViewModel", "updatedFavoriteStatus: ${_isFavorite.value}")
        }
    }

    private fun checkFavorite(drink: Drink): LiveData<Boolean> {
        return database.drinkDao.checkFavoriteById(drink.idDrink)
    }

    fun favoriteBtnActive() {
        Log.e("DetailViewModel", "favoriteBtnActive")
    }

    fun favoriteBtnInactive() {
        Log.e("DetailViewModel", "favoriteBtnInactive")

    }

    fun updateFavoriteStatus() {
        _isFavorite.value = _isFavorite.value != true
        Log.e("DetailViewModel", "updatedFavoriteStatus: ${_isFavorite.value}")
    }

    fun viewBinding(drink: Drink, binding: FragmentDetailBinding) {
        binding.strDrink.text = drink.strDrink
        binding.strCategory.text = drink.strCategory
        binding.strGlass.text = drink.strGlass
        binding.strInstructions.text = drink.strInstructions

        binding.strDrinkThumb
        Glide.with(binding.strDrinkThumb.context)
            .load(drink.strDrinkThumb)
            .apply(
                RequestOptions()
                    .placeholder(R.drawable.loading_animation)
                    .error(R.drawable.ic_broken_image)
            )
            .into(binding.strDrinkThumb)
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