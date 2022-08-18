package com.example.android.mycocktailtesting.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.android.mycocktailtesting.data.database.DrinkDatabase
import com.example.android.mycocktailtesting.data.network.CocktailDbAPI
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.verify
import org.mockito.Mockito.`when`
import org.mockito.kotlin.eq


@RunWith(MockitoJUnitRunner::class)
class DrinksRepositoryTest {
//
//    private lateinit var defaultDrinksRepository: DefaultDrinksRepository
//
//    @Mock
//    lateinit var drinkDatabase: DrinkDatabase
//
//    @Mock
//    lateinit var cocktailDbAPI: CocktailDbAPI
//
//    @get:Rule
//    var rule: TestRule = InstantTaskExecutorRule()
//
//    @Before
//    fun setup() {
//        defaultDrinksRepository = DefaultDrinksRepository(drinkDatabase, cocktailDbAPI)
//    }
//
//    @After
//    fun tearDown() {
//
//    }
//
////    [UnitOfWork_StateUnderTest_ExpectedBehavior]
////    equal, verify, mock, when
////    thenReturn
//
//
//    @Test
//    fun getFilterList() {
////        //build
////        val result = liftOf
////
////        //operate
////            drinksRepository.filterList
////
////        //verify
//
//    }
//
//    @Test
//    fun getRandomDrinks() {
//    }
//
//    @Test
//    fun getPopularDrinks() {
//    }
//
//    @Test
//    fun getLatestDrinks() {
//    }
//
//    @Test
//    fun getFavoriteDrinks() {
//    }
//
//    @Test
//    fun refreshRandomDrinks_oneRandomDrink_oneRandomDrinkInserted()  {
//        // build
//        `when`(cocktailDbAPI.getRandomCocktails()).thenReturn(listOf("longIslandTea"));
//
//        // operate
//        runBlocking { defaultDrinksRepository.refreshRandomDrinks() }
//
//        // verify
//        verify(drinkDatabase.drinkDao).insertAllRandomDrinks(eq(listOf("longIslandTea")));
//    }
//
//    @Test
//    fun refreshPopularDrinks() {
//    }
//
//    @Test
//    fun refreshLatestDrinks() {
//    }
//
//    @Test
//    fun checkIsFavorite() {
//    }
//
//    @Test
//    fun insertFavoriteDrink() {
//    }
//
//    @Test
//    fun deleteFavoriteDrink() {
//    }
}