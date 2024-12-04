package com.example.recipeapp_projectfinal.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recipes")
data class NewRecipe(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val image: ByteArray,
    val readyInMinutes: Int,
    val ingredients: String,
    val sourceUrl: String = "",
    val creator: String? = null
)
