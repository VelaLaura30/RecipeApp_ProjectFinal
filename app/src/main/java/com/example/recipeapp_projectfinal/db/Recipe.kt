package com.example.recipeapp_projectfinal.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recipes")
data class Recipe(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val userId: Long, // Relacionado al ID del usuario
    val title: String,
    val description: String,
    val ingredients: String,
    val instructions: String,
    val image: String
)

