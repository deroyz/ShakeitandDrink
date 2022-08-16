package com.example.android.mycocktailtesting.di

import com.example.android.mycocktailtesting.data.database.DrinkDatabase
import com.example.android.mycocktailtesting.data.domain.Drink
import com.example.android.mycocktailtesting.data.network.CocktailDbAPI
import com.example.android.mycocktailtesting.data.repository.DefaultDrinksRepository
import com.example.android.mycocktailtesting.data.repository.DrinksRepository
import com.example.android.mycocktailtesting.data.repository.LogsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideDefaultDrinksRepository(
        database: DrinkDatabase,
        cocktailDbAPI: CocktailDbAPI
    ) = DefaultDrinksRepository(database, cocktailDbAPI) as DrinksRepository

    @Provides
    @Singleton
    fun provideLogsRepository(database: DrinkDatabase): LogsRepository = LogsRepository(database)

}