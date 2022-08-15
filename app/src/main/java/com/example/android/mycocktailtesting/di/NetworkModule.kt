package com.example.android.mycocktailtesting.di

import com.example.android.mycocktailtesting.other.Constants.BASE_URL
import com.example.android.mycocktailtesting.data.network.CocktailDbAPI
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideCocktailDbService(): CocktailDbAPI {
        val moshi = MoshiConverterFactory.create(
            Moshi.Builder()
                .add(KotlinJsonAdapterFactory())
                .build()
        )
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(moshi)
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
            .create(CocktailDbAPI::class.java)
    }
}