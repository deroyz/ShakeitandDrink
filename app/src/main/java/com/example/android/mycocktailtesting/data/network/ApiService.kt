package com.example.android.mycocktailtesting.data.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

interface CocktailDbAPI{
    @GET("randomselection.php")
    fun getRandomCocktails(): Deferred<NetworkDrinkContainer>

    @GET("popular.php")
    fun getPopularCocktails(): Deferred<NetworkDrinkContainer>

    @GET("latest.php")
    fun getLatestCocktails(): Deferred<NetworkDrinkContainer>

    @GET("list.php?i=list")
    fun getIngredientList(): Deferred<NetworkDrinkContainer>
}
