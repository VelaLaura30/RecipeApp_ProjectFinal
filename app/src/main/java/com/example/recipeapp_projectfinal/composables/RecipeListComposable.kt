package com.example.recipeapp_projectfinal.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.recipeapp_projectfinal.data.api.models.Recipe

@Composable
fun RecipeList(recipes: List<Recipe>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)  // Mayor espaciado entre recetas
    ) {
        items(recipes) { recipe ->
            RecipeItemSimple(recipe)
        }
    }
}

@Composable
fun RecipeItemSimple(recipe: Recipe) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(MaterialTheme.colorScheme.surfaceVariant, shape = RoundedCornerShape(12.dp))  // Fondo con borde redondeado
            .shadow(4.dp, shape = RoundedCornerShape(12.dp))  // Sombra ligera para elevar el elemento
            .padding(16.dp)
    ) {
        // Mostrar Título
        Text(
            text = recipe.title,
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.padding(bottom = 8.dp)  // Espaciado debajo del título
        )

        // Mostrar Imagen (opcional)
        AsyncImage(
            model = recipe.image,
            contentDescription = recipe.title,
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)  // Imagen un poco más grande para mejor visualización
                .clip(RoundedCornerShape(12.dp))  // Bordes redondeados en la imagen
                .padding(bottom = 8.dp)  // Espaciado debajo de la imagen
        )
        }
    }

