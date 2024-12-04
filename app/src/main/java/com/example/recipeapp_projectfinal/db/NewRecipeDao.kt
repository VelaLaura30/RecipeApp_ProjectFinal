package com.example.recipeapp_projectfinal.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query


@Dao
interface NewRecipeDao {
    @Insert
    suspend fun insertRecipe(recipe: NewRecipe)

    @Query("SELECT * FROM recipes")
    suspend fun getAllRecipes(): List<NewRecipe>

    @Query("DELETE FROM recipes")
    suspend fun deleteAllRecipes()

}

