package com.example.recipeapp_projectfinal.data.api.models

data class RandomRecipe(
    val id: Int,
    val title: String,
    val image: String,
    val readyInMinutes: Int,
    val sourceUrl: String,
    val aggregateLikes: Int,
    val spoonacularScore: Double
)
