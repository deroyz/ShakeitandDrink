/*
 * Copyright 2018, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package com.example.android.mycocktailtesting.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.*
import com.example.android.mycocktailtesting.domain.Drink

enum class CocktailDatabaseFilter(val value: String) {
    SHOW_TODAYS("todays"), SHOW_POPULAR("popular"), SHOW_FAVORITE(
        "favorite"
    )
}

@Dao
interface DrinkDao {

    // RandomDrinks DAO
    @Query("select * from randomdrinks")
    fun getRandomDrinks(): LiveData<List<DatabaseRandomDrink>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllRandomDrinks(vararg drinks: DatabaseRandomDrink)


    // PopularDrinks DAO
    @Query("select * from populardrinks")
    fun getPopularDrinks(): LiveData<List<DatabasePopularDrink>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllPopularDrinks(vararg drinks: DatabasePopularDrink)


    // FavoriteDrinks DAO
    @Query("select * from favoritedrinks")
    fun getFavoriteDrinks(): LiveData<List<DatabaseFavoriteDrink>>

    @Query("SELECT EXISTS(select * from favoritedrinks WHERE idDrink = :id)")
    fun checkFavoriteById(id: Double): Boolean

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavoriteDrink(newFavoriteDrink: DatabaseFavoriteDrink)

    @Query("DELETE FROM favoritedrinks WHERE idDrink = :id")
    fun deleteFavoriteDrink(id: Double)
}

@Database(entities = [DatabaseRandomDrink::class, DatabasePopularDrink::class, DatabaseFavoriteDrink::class], version = 1)
abstract class DrinkDatabase : RoomDatabase() {
    abstract val drinkDao: DrinkDao
}

private lateinit var INSTANCE: DrinkDatabase

fun getDatabase(context: Context): DrinkDatabase {
    synchronized(DrinkDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(
                context.applicationContext,
                DrinkDatabase::class.java,
                "drinks"
            ).build()
        }
    }
    return INSTANCE
}
