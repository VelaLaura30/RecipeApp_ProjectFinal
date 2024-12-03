package com.example.recipeapp_projectfinal.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.example.recipeapp_projectfinal.data.api.models.Recipe
import com.example.recipeapp_projectfinal.data.viewmodel.RecipeSearchViewModel

@Composable
fun RecipeListScreen(
    searchQuery: String,
    viewModel: RecipeSearchViewModel
) {
    // Observa el estado de las recetas desde el ViewModel
    val recipes by viewModel.recipes
    val isLoading by viewModel.isLoading
    val error by viewModel.error

    // Layout principal
    Column(modifier = Modifier.padding(16.dp)) {
        if (isLoading) {
            // Mostrar un indicador de carga mientras se buscan las recetas
            Text("Cargando recetas...")
        } else if (error != null) {
            // Mostrar mensaje de error si hay algún problema
            Text("Error: $error")
        } else if (recipes.isNotEmpty()) {
            // Mostrar las recetas filtradas si están disponibles
            LazyColumn {
                items(recipes) { recipe ->
                    RecipeItem(recipe) // Llamamos a un Composable para mostrar cada receta
                }
            }
        } else {
            // Si no hay recetas, mostrar un mensaje
            //Text("No se encontraron recetas.")
        }
    }
}

@Composable
fun RecipeItem(recipe: Recipe) {
    // Tarjeta para cada receta
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Mostrar la imagen de la receta
            Image(
                painter = rememberImagePainter(recipe.image), // Cargar imagen desde la URL
                contentDescription = recipe.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp) // Ajusta el tamaño de la imagen
            )

            // Mostrar el título de la receta
            Text(
                text = recipe.title,
                //style = MaterialTheme.typography.h6,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}
