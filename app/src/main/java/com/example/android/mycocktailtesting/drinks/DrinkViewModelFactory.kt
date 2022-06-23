package com.example.android.mycocktailtesting.drinks

import android.app.Application
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider


class DrinkViewModelFactory(private val app: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DrinkViewModel::class.java)) {
            Log.e("ViewModelFactory", "assigned")
            return DrinkViewModel(app) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

