package com.example.android.mycocktailtesting.ui.drinks

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.android.mycocktailtesting.data.database.CocktailDatabaseFilter
import com.example.android.mycocktailtesting.data.domain.Drink
import com.example.android.mycocktailtesting.data.repository.FakeDrinksRepository
import org.junit.Assert.*

import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mockito.mock

class DrinksViewModelTest {

    private lateinit var drinksViewModel: DrinksViewModel

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

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

    @Test
    fun updateFilter_nonNullFilter_filterIsAssigned() {
        // build
        val filter = mock(CocktailDatabaseFilter::class.java)

        // operate
        drinksViewModel.updateFilter(filter)

        // verify
        assertEquals(drinksViewModel.filter.value, filter)
    }

    @Test
    fun navigateToSelectedDrink() {
        //build
        val drink = mock(Drink::class.java)

        //operate
        drinksViewModel.navigateToSelectedDrink(drink)

        //verify
        assertEquals(drinksViewModel.navigateToSelectedDrink, drink)

    }

}