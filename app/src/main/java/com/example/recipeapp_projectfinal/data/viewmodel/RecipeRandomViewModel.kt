package com.example.recipeapp_projectfinal.data.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipeapp_projectfinal.data.api.models.PreparationRecipe
import com.example.recipeapp_projectfinal.data.api.models.RandomRecipe
import com.example.recipeapp_projectfinal.data.api.models.Recipe
import kotlinx.coroutines.launch

class RecipeRandomViewModel : ViewModel() {
    val randomRecipes = mutableStateOf<List<RandomRecipe>>(emptyList())
    val isLoading = mutableStateOf(false)
    val error = mutableStateOf<String?>(null)
    val allRecipes = mutableStateOf<List<RandomRecipe>>(emptyList())
    val recipeDetails = mutableStateOf<PreparationRecipe?>(null)

    val _favoriteRecipes = mutableStateListOf<Recipe>()
    val favoriteRecipes: List<Recipe> = _favoriteRecipes

    fun addToFavorites(recipe: Recipe) {
        if (!_favoriteRecipes.contains(recipe)) {
            _favoriteRecipes.add(recipe)
        }
    }

    fun removeFromFavorites(recipe: Recipe) {
        _favoriteRecipes.remove(recipe)
    }

    fun getRandomRecipe(number: Int) {
        isLoading.value = true
        error.value = null

        Log.d("RecipeRandomViewModel", "Inicio de la llamada a la API")
        viewModelScope.launch {
            try {

                val response = RetrofitService.apiService.getRandomRecipes(number)

                Log.d("RecipeRandomViewModel", "Response: $response")

                if (response.recipes.isNotEmpty()) {
                    randomRecipes.value = response.recipes
                } else {
                    error.value = "No se encontraron recetas"
                }
            } catch (e: Exception) {
                error.value = e.message
                Log.e("RecipeRandomViewModel", "Error: ${e.message}", e)
            } finally {
                isLoading.value = false
            }
        }
    }

    fun searchRecipes(query: String) {
        if (query.isBlank()) {

            randomRecipes.value = allRecipes.value
        } else {

            randomRecipes.value = allRecipes.value.filter { recipe ->
                recipe.title.contains(query, ignoreCase = true)
            }
        }
    }

    fun fetchRecipeById(recipeId: Int) {
        viewModelScope.launch {
            try {
                val recipe = RetrofitService.apiService.getRecipeById(recipeId)
                recipeDetails.value = recipe
            } catch (e: Exception) {
                Log.e("RecipeRandomViewModel", "Error fetching recipe by ID: ${e.message}", e)
                recipeDetails.value = null
            }
        }
    }



}

