package com.example.recipeapp_projectfinal.data.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipeapp_projectfinal.data.api.models.RandomRecipe
import kotlinx.coroutines.launch

class RecipeRandomViewModel : ViewModel() {
    val randomRecipes = mutableStateOf<List<RandomRecipe>>(emptyList())
    val isLoading = mutableStateOf(false)
    val error = mutableStateOf<String?>(null)
    val allRecipes = mutableStateOf<List<RandomRecipe>>(emptyList())

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
            // Mostrar todas las recetas si no hay búsqueda
            randomRecipes.value = allRecipes.value
        } else {
            // Filtrar recetas por el nombre
            randomRecipes.value = allRecipes.value.filter { recipe ->
                recipe.title.contains(query, ignoreCase = true)
            }
        }
    }

}

