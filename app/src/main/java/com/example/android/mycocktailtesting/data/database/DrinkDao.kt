package com.example.android.mycocktailtesting.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface DrinkDao {

    // RandomDrinks DAO
    @Query("SELECT * FROM randomdrinks")
    fun getAllRandomDrinks(): LiveData<List<DatabaseRandomDrink>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllRandomDrinks(vararg drinks: DatabaseRandomDrink)


    // PopularDrinks DAO
    @Query("SELECT * FROM populardrinks")
    fun getAllPopularDrinks(): LiveData<List<DatabasePopularDrink>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllPopularDrinks(vararg drinks: DatabasePopularDrink)

    // LatestDrinks DAO
    @Query("SELECT * FROM latestdrinks")
    fun getAllLatestDrinks(): LiveData<List<DatabaseLatestDrink>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllLatestDrinks(vararg drinks: DatabaseLatestDrink)


    // FavoriteDrinks DAO
    @Query("SELECT * FROM favoritedrinks")
    fun getAllFavoriteDrinks(): LiveData<List<DatabaseFavoriteDrink>>

    @Query("SELECT EXISTS(SELECT * FROM favoritedrinks WHERE idDrink = :id)")
    fun checkFavoriteById(id: Double): Boolean

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavoriteDrink(newFavoriteDrink: DatabaseFavoriteDrink)

    @Query("DELETE FROM favoritedrinks WHERE idDrink = :id")
    fun deleteFavoriteDrinkById(id: Double)
}
