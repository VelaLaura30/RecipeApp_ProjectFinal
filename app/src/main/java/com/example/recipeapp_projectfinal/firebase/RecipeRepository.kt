/*package com.example.recipeapp_projectfinal.firebase

import com.example.recipeapp_projectfinal.db.Recipe
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class RecipeRepository {

    private val db = FirebaseFirestore.getInstance()
    private val recipesCollection = db.collection("recipes")

    // Función para agregar una receta
    suspend fun addRecipe(recipe: Recipe) {
        recipesCollection.add(recipe).await()
    }

    // Función para obtener todas las recetas
    suspend fun getAllRecipes(): List<Recipe> {
        return recipesCollection.get().await().toObjects(Recipe::class.java)
    }
}*/