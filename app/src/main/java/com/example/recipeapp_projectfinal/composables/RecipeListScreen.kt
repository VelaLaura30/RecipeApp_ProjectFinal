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

    val recipes by viewModel.recipes
    val isLoading by viewModel.isLoading
    val error by viewModel.error


    Column(modifier = Modifier.padding(16.dp)) {
        if (isLoading) {

            Text("Cargando recetas...")
        } else if (error != null) {

            Text("Error: $error")
        } else if (recipes.isNotEmpty()) {

            LazyColumn {
                items(recipes) { recipe ->
                    RecipeItem(recipe)
                }
            }
        } else {
        }
    }
}

@Composable
fun RecipeItem(recipe: Recipe) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            Image(
                painter = rememberImagePainter(recipe.image),
                contentDescription = recipe.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )

            Text(
                text = recipe.title,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}
