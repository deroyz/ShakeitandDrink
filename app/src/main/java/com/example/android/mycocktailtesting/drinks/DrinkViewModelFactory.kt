package com.example.android.mycocktailtesting.drinks

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider


class DrinkViewModelFactory(val app: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DrinkViewModelFactory::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DrinkViewModelFactory(app) as T
        }
        throw IllegalArgumentException("Unable to construct viewmodel")
    }
}
