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
package com.example.android.mycocktailtesting.data.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.android.mycocktailtesting.data.domain.DomainLog

enum class CocktailDatabaseFilter(val value: String) {
    SHOW_TODAYS("Todays"),
    SHOW_POPULAR("Popular"),
    SHOW_LATEST("Latest"),
    SHOW_FAVORITE("Favorite")
}

@Database(
    entities = [DatabaseRandomDrink::class, DatabasePopularDrink::class, DatabaseFavoriteDrink::class, DatabaseLatestDrink::class, DatabaseLog::class],
    version = 1
)
abstract class DrinkDatabase : RoomDatabase() {
    abstract val drinkDao: DrinkDao
    abstract val logDao: LogDao
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


