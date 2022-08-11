package com.example.android.mycocktailtesting.di

import android.content.Context
import androidx.room.Room
import com.example.android.mycocktailtesting.database.DrinkDao
import com.example.android.mycocktailtesting.database.DrinkDatabase
import com.example.android.mycocktailtesting.database.LogDao
import com.example.android.mycocktailtesting.domain.Drink
import com.example.android.mycocktailtesting.repository.DrinksRepository
import com.example.android.mycocktailtesting.repository.LogsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    fun provideLogDao(database: DrinkDatabase): LogDao {
        return database.logDao
    }

    @Provides
    fun provideDrinkDao(database: DrinkDatabase): DrinkDao {
        return database.drinkDao
    }

    @Provides
    @Singleton
    fun provideDrinksRepository(database: DrinkDatabase): DrinksRepository = DrinksRepository(database)

    @Provides
    @Singleton
    fun provideLogsRepository(database: DrinkDatabase): LogsRepository = LogsRepository(database)

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): DrinkDatabase {
        return Room.databaseBuilder(
            appContext.applicationContext,
            DrinkDatabase::class.java,
            "drinks"
        ).build()
    }


}