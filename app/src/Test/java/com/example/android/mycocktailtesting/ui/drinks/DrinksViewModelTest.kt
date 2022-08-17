package com.example.android.mycocktailtesting.ui.drinks

import com.example.android.mycocktailtesting.data.repository.FakeDrinksRepository
import org.junit.Assert.*

import org.junit.After
import org.junit.Before
import org.junit.Test

class DrinksViewModelTest {

    private lateinit var drinksViewModel: DrinksViewModel

    @Before
    fun setUp() {
        drinksViewModel = DrinksViewModel(FakeDrinksRepository())
    }

    @After
    fun tearDown() {
    }

    @Test
    fun `insert favorite drinks with empty field, returns error` (){
    }
}