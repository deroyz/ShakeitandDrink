package com.example.android.mycocktailtesting.di

import com.example.android.mycocktailtesting.model.database.DrinkDatabase
import com.example.android.mycocktailtesting.model.network.CocktailDBService
import com.example.android.mycocktailtesting.model.repository.DrinksRepository
import com.example.android.mycocktailtesting.model.repository.LogsRepository
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
    fun provideDrinksRepository(
        database: DrinkDatabase,
        cocktailDBService: CocktailDBService
    ): DrinksRepository = DrinksRepository(database, cocktailDBService)

    @Provides
    @Singleton
    fun provideLogsRepository(database: DrinkDatabase): LogsRepository = LogsRepository(database)

}