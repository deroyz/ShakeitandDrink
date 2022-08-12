package com.example.android.mycocktailtesting.model.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

private const val BASE_URL = "https://www.thecocktaildb.com/api/json/v2/9973533/"

interface CocktailDBService{
    @GET("randomselection.php")
    fun getRandomCocktails(): Deferred<NetworkDrinkContainer>

    @GET("popular.php")
    fun getPopularCocktails(): Deferred<NetworkDrinkContainer>

    @GET("latest.php")
    fun getLatestCocktails(): Deferred<NetworkDrinkContainer>

    @GET("list.php?i=list")
    fun getIngredientList(): Deferred<NetworkDrinkContainer>
}

/**
 * Build the Moshi object that Retrofit will be using, making sure to add the Kotlin adapter for
 * full Kotlin compatibility.
 */
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

/**
 * Main entry point for network access. Call like `Network.cocktailDBService.getRandomCocktails()`
 */
object Network{
    // Configure retrofit to parse JSON and use coroutines
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .build()

    val cocktailDBService = retrofit.create(CocktailDBService::class.java)
}