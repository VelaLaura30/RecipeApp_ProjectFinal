package com.example.recipeapp_projectfinal.data.api.models

data class RandomRecipe(
    /*val id: Int,
    val title: String,
    val image: String,
    val readyInMinutes: Int,
    val sourceUrl: String,
    val aggregateLikes: Int,
    val spoonacularScore: Double,
    val sourceName: String*/

    val id: Int,
    val title: String,
    val image: String,
    val servings:Int,
    val readyInMinutes: Int,
    val cookingMinutes: Int,
    val preparationMinutes: Int,
    val aggregateLikes: Int,
    val spoonacularScore: Double,
    val healthScore: Double,
    val vegan: Boolean,
    val vegetarian: Boolean,
    val veryHealthy: Boolean,
    val veryPopular: Boolean,
    val spoonacularSourceUrl: String,
    val sourceUrl: String
)
