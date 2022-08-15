package com.example.android.mycocktailtesting.data.database

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.example.android.mycocktailtesting.getOrAwaitValue
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class DrinkDaoTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var drinkDatabase: DrinkDatabase
    private lateinit var drinkDao: DrinkDao

    @Before
    fun setup() {
        drinkDatabase = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            DrinkDatabase::class.java
        ).allowMainThreadQueries().build()
        drinkDao = drinkDatabase.drinkDao
    }

    @After
    fun teardown() {
        drinkDatabase.close()
    }

    @Test
    fun insertAllRandomDrinks() {

        // Build
        val drink = DatabaseRandomDrink(
            idDrink = 14360.0,
            strDrink = "Absolut Sex",
            strCategory = "Shot",
            strGlass = "Old-fashioned glass",
            strDrinkThumb = "https://www.thecocktaildb.com/images/media/drink/xtrvtx1472668436.jpg",
            strInstructions = "Shake Absolut Kurant, Midori, and Cranberry juice in shaker with ice: Strain into rocks glass: Splash of seven on top.Absolut Sex."
        )

        // Operate
        drinkDao.insertAllRandomDrinks(drink)

        // Verify
        val allRandomDrinks = drinkDao.getAllRandomDrinks().getOrAwaitValue()
        assertThat(allRandomDrinks).contains(drink)
    }

    @Test
    fun insertFavoriteDrink() {

        // Build
        val favoriteDrink = DatabaseFavoriteDrink(
            idDrink = 14360.0,
            strDrink = "Absolut Sex",
            strCategory = "Shot",
            strGlass = "Old-fashioned glass",
            strDrinkThumb = "https://www.thecocktaildb.com/images/media/drink/xtrvtx1472668436.jpg",
            strInstructions = "Shake Absolut Kurant, Midori, and Cranberry juice in shaker with ice: Strain into rocks glass: Splash of seven on top.Absolut Sex."
        )

        // Operate
        drinkDao.insertFavoriteDrink(favoriteDrink)

        // Verify
        val allFavoriteDrink = drinkDao.getAllFavoriteDrinks().getOrAwaitValue()
        assertThat(allFavoriteDrink).contains(favoriteDrink)
    }

    @Test
    fun deleteFavoriteDrink() {

        // Build
        val favoriteDrink = DatabaseFavoriteDrink(
            idDrink = 14360.0,
            strDrink = "Absolut Sex",
            strCategory = "Shot",
            strGlass = "Old-fashioned glass",
            strDrinkThumb = "https://www.thecocktaildb.com/images/media/drink/xtrvtx1472668436.jpg",
            strInstructions = "Shake Absolut Kurant, Midori, and Cranberry juice in shaker with ice: Strain into rocks glass: Splash of seven on top.Absolut Sex."
        )
        drinkDao.insertFavoriteDrink(favoriteDrink)

        // Operate
        drinkDao.deleteFavoriteDrinkById(favoriteDrink.idDrink)

        // Verify
        val allFavoriteDrink = drinkDao.getAllFavoriteDrinks().getOrAwaitValue()
        assertThat(allFavoriteDrink).doesNotContain(allFavoriteDrink)
    }

    @Test
    fun checkFavoriteById(){
        // Build
        val favoriteDrink = DatabaseFavoriteDrink(
            idDrink = 14360.0,
            strDrink = "Absolut Sex",
            strCategory = "Shot",
            strGlass = "Old-fashioned glass",
            strDrinkThumb = "https://www.thecocktaildb.com/images/media/drink/xtrvtx1472668436.jpg",
            strInstructions = "Shake Absolut Kurant, Midori, and Cranberry juice in shaker with ice: Strain into rocks glass: Splash of seven on top.Absolut Sex."
        )
        drinkDao.insertFavoriteDrink(favoriteDrink)

        // Operate
        val isFavorite = drinkDao.checkFavoriteById(favoriteDrink.idDrink)

        // Verify
        assertThat(isFavorite).isTrue()
    }
}