package com.example.recipeapp_projectfinal.data.api.retrofit

import com.example.recipeapp_projectfinal.data.api.models.RandomRecipeResponse
import com.example.recipeapp_projectfinal.data.api.models.RecipeSearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("recipes/random")
    suspend fun getRandomRecipes(
        @Query("number") number: Int = 10,
        ): RandomRecipeResponse

    @GET("recipes/complexSearch")
    suspend fun searchRecipes(
        @Query("query") query: String,
        @Query("type") type: String?,
        //@Query("maxFat") maxFat: Int?,
        //@Query("number") number: Int
    ): RecipeSearchResponse



}