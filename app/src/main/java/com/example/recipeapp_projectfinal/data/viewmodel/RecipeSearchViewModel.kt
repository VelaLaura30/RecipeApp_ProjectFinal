package com.example.recipeapp_projectfinal.data.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipeapp_projectfinal.data.api.models.Recipe
import kotlinx.coroutines.launch

class RecipeSearchViewModel : ViewModel() {
    private val _recipes = mutableStateOf<List<Recipe>>(emptyList())
    val recipes: State<List<Recipe>> = _recipes

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    private val _error = mutableStateOf<String?>(null)
    val error: State<String?> = _error


    fun searchRecipes(query: String, type: String?) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val tipoPredefinido = "main course"
                Log.d("FILTER_LOG", "Filtrando recetas con el tipo: $tipoPredefinido")
                val response = RetrofitService.apiService.searchRecipes(
                    query = query,
                    type = tipoPredefinido
                )


                _recipes.value = response.results


                Log.d("FILTER_LOG", "Recetas filtradas: ${response.results.joinToString { it.title }}")

                _error.value = null
            } catch (e: Exception) {
                _error.value = "Error: ${e.message}"
                Log.e("FILTER_LOG", "Error al buscar recetas: ${e.message}")
            } finally {
                _isLoading.value = false
            }
        }
    }
}
