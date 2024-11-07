package com.example.recipeapp_projectfinal.data.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipeapp_projectfinal.data.api.models.RandomRecipe
import kotlinx.coroutines.launch

class RecipeRandomViewModel : ViewModel() {
    val randomRecipe = mutableStateOf<RandomRecipe?>(null)
    val isLoading = mutableStateOf(false)
    val error = mutableStateOf<String?>(null)

    fun getRandomRecipe() {
        isLoading.value = true
        error.value = null

        Log.d("RecipeRandomViewModel", "Inicio de la llamada a la API")
        viewModelScope.launch {
            try {
                // Realiza la llamada a la API
                val response = RetrofitService.apiService.getRandomRecipes()

                Log.d("RecipeRandomViewModel", "Response: $response")
                // Verifica si la lista de recetas no está vacía
                if (response.recipes.isNotEmpty()) {
                    randomRecipe.value = response.recipes[0] // Accede al primer elemento de la lista
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
}

