package com.example.recipeapp_projectfinal.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.recipeapp_projectfinal.R
import com.example.recipeapp_projectfinal.data.api.models.PreparationRecipe
import com.example.recipeapp_projectfinal.data.api.models.RandomRecipe

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PreparationScreen(preparationRecipe: PreparationRecipe) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(preparationRecipe.title) }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            Image(
                painter = rememberAsyncImagePainter(preparationRecipe.image),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(MaterialTheme.shapes.medium)
            )


            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {

                Text("Información Básica", style = MaterialTheme.typography.titleMedium)
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    InfoCard("Porciones", preparationRecipe.servings.toString(), R.drawable.servings)
                    InfoCard("Listo en", "${preparationRecipe.readyInMinutes} min", R.drawable.timer)
                    InfoCard("Cocción", "${preparationRecipe.cookingMinutes} min", R.drawable.preparation)
                    InfoCard("Preparación", "${preparationRecipe.preparationMinutes} min", R.drawable.icon_app)
                }


                Text("Puntuación", style = MaterialTheme.typography.titleMedium)
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    InfoCard("Likes", preparationRecipe.aggregateLikes.toString(), R.drawable.like)
                    InfoCard("Puntuación", preparationRecipe.spoonacularScore.toString(), R.drawable.puntuacion)
                }


                Text("Características Saludables", style = MaterialTheme.typography.titleMedium)
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    InfoCard("Salud", preparationRecipe.healthScore.toString(), R.drawable.salud)
                    InfoCard("Vegano", if (preparationRecipe.vegan) "Sí" else "No", R.drawable.vegano)
                    InfoCard("Vegetariano", if (preparationRecipe.vegetarian) "Sí" else "No", R.drawable.vegetariano)
                    InfoCard("Popular", if (preparationRecipe.veryPopular) "Sí" else "No", R.drawable.popular)
                }
            }


            Button(
                onClick = {  },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text("Más información")
            }
        }
    }
}

@Composable
fun InfoCard(label: String, value: String, iconResId: Int) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.width(80.dp)
    ) {
        Image(
            painter = painterResource(id = iconResId),
            contentDescription = null,
            modifier = Modifier.size(24.dp)
        )
        Text(label, style = MaterialTheme.typography.bodySmall)
        Text(value, style = MaterialTheme.typography.bodyMedium)
    }
}



