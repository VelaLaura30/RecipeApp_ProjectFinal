package com.example.recipeapp_projectfinal.composables

import NewRecipeViewModel
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.recipeapp_projectfinal.R
import com.example.recipeapp_projectfinal.db.NewRecipe

@Composable
fun AllRecipesScreen(viewModel: NewRecipeViewModel) {
    val recipes = viewModel.allRecipes.value

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("Recetas Creadas", style = MaterialTheme.typography.titleLarge)

        if (recipes.isEmpty()) {
            Text("No hay recetas creadas aún.")
        } else {
            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                recipes.forEach { recipe ->
                    RecipeCard(recipe = recipe)
                }
            }
        }
    }
}

@Composable
fun RecipeCard(recipe: NewRecipe) {
    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            recipe.image?.let {
                Image(
                    painter = rememberAsyncImagePainter(it),
                    contentDescription = "Imagen de la receta",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(80.dp)
                        .padding(end = 16.dp)
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = recipe.title,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Tiempo Preparación: ${recipe.readyInMinutes} minutos",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Ingredientes: ${recipe.ingredients}",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray,
                    maxLines = 1
                )
                Text(
                    text = "Creada por: ${recipe.creator ?: "Desconocido"}",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray,
                    maxLines = 1
                )

            }
        }
    }
}
