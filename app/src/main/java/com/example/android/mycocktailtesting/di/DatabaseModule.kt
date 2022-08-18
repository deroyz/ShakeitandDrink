package com.example.android.mycocktailtesting.di

import android.content.Context
import androidx.room.Room
import com.example.android.mycocktailtesting.other.Constants.DATABASE_NAME
import com.example.android.mycocktailtesting.data.database.DrinkDao
import com.example.android.mycocktailtesting.data.database.DrinkDatabase
import com.example.android.mycocktailtesting.data.database.LogDao
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
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): DrinkDatabase {
        return Room.databaseBuilder(
            appContext, DrinkDatabase::class.java, DATABASE_NAME
        ).build()
    }

    @Provides
    fun provideLogDao(database: DrinkDatabase): LogDao {
        return database.logDao
    }

    @Provides
    fun provideDrinkDao(database: DrinkDatabase): DrinkDao {
        return database.drinkDao
    }

}