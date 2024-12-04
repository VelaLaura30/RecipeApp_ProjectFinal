package com.example.recipeapp_projectfinal.data.api.models

data class Recipes(
    val id: Int,
    val title: String,
    val image: ByteArray,
    val readyInMinutes: Int,
    val ingredients: String,
    val sourceUrl: String
)
