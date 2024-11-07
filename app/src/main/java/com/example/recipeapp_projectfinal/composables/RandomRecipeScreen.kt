import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.ui.res.painterResource
import coil.compose.rememberImagePainter
import com.example.recipeapp_projectfinal.R
import com.example.recipeapp_projectfinal.data.api.models.RandomRecipe
import com.example.recipeapp_projectfinal.data.viewmodel.RecipeRandomViewModel

@Composable
fun RandomRecipeScreen(viewModel: RecipeRandomViewModel) {
    val recipes = viewModel.randomRecipe.value
    val isLoading = viewModel.isLoading.value
    val error = viewModel.error.value

    // Cargando datos
    if (isLoading) {
        CircularProgressIndicator(modifier = Modifier.fillMaxSize())
    } else if (error != null) {
        // Mostrar mensaje de error
        Text(text = "Error: $error", color = Color.Red, modifier = Modifier.fillMaxSize(), style = MaterialTheme.typography.bodyLarge)
    } else {
        // Mostrar recetas
        Column(modifier = Modifier.fillMaxSize()) {
            // Titulo y descripcion de la pantalla
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Bienvenido a recetas deliciosas",
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Text(
                    text = "Descubre nuevas recetas que puedes probar hoy. ¡Cada receta es una sorpresa!",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.Gray
                )
            }

            if (recipes != null) {
                RecipeCard(recipes)
            }
        }
    }
}

@Composable
fun RecipeCard(recipe: RandomRecipe) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        shape = MaterialTheme.shapes.medium
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // Imagen de la receta
            Image(
                painter = rememberImagePainter(recipe.image),
                contentDescription = recipe.title,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

            // Nombre de la receta
            Text(
                text = recipe.title,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            // Detalles de la receta con iconos
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(id = R.drawable.timer),
                    contentDescription = "Tiempo de preparación",
                    modifier = Modifier.size(20.dp),
                    tint = Color.Gray
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "${recipe.readyInMinutes} min",
                    style = MaterialTheme.typography.bodySmall
                )
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Filled.Favorite,
                    contentDescription = "Likes",
                    modifier = Modifier.size(20.dp),
                    tint = Color.Gray
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "${recipe.aggregateLikes} Likes",
                    style = MaterialTheme.typography.bodySmall
                )
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(id = R.drawable.puntuacion), // Reemplaza con tu recurso de icono
                    contentDescription = "Puntuacion",
                    modifier = Modifier.size(20.dp),
                    tint = Color.Gray
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "${recipe.spoonacularScore}/100",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}
