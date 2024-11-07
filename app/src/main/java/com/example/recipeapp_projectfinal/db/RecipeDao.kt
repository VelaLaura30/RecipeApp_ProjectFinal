package com.example.recipeapp_projectfinal.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface RecipeDao {

    @Insert
    suspend fun insertRecipe(recipe: Recipe)

    @Query("SELECT * FROM recipes WHERE userId = :userId")
    suspend fun getRecipesForUser(userId: Long): List<Recipe>
}
