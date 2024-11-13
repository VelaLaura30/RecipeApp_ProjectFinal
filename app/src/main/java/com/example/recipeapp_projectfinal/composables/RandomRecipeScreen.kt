import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Menu
//import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.res.painterResource
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.draw.clip
import coil.compose.AsyncImage
import com.example.recipeapp_projectfinal.R
import com.example.recipeapp_projectfinal.data.api.models.RandomRecipe
import com.example.recipeapp_projectfinal.data.viewmodel.RecipeRandomViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RandomRecipeScreen(viewModel: RecipeRandomViewModel) {
    val recipes = viewModel.randomRecipes.value
    val isLoading = viewModel.isLoading.value
    val error = viewModel.error.value
    var searchQuery by remember { mutableStateOf("") }
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            Column(
                modifier = Modifier
                    .background(Color.White)
                    .fillMaxSize() // Fondo blanco para el menú lateral
            ) {
                Text(text = "Menú", style = MaterialTheme.typography.headlineMedium)
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = "Inicio")
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Favoritos")
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Actividad de usuarios")
            }
        }
        ,
        content = {
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = { Text("Recetas") },
                        navigationIcon = {
                            IconButton(
                                onClick = {
                                    coroutineScope.launch {
                                        // Cambia el estado del drawer cada vez que el botón es presionado
                                        if (drawerState.isClosed) drawerState.open() else drawerState.close()
                                    }
                                }
                            ) {
                                Icon(Icons.Filled.Menu, contentDescription = "Menu")
                            }
                        },
                        actions = {
                            IconButton(onClick = { /* Acción para filtrar recetas */ }) {
                                Icon(Icons.Filled.List, contentDescription = "Filtro")
                            }
                        }
                    )
                },
                floatingActionButton = {
                    FloatingActionButton(onClick = { /* Acción para agregar receta */ }) {
                        Icon(Icons.Filled.Add, contentDescription = "Agregar receta")
                    }
                }
            ) { paddingValues ->
                Column(
                    modifier = Modifier
                        .padding(paddingValues)
                        .fillMaxSize()
                ) {
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
                        Text(
                            text = "Recetas del dia de hoy",
                            style = MaterialTheme.typography.bodyLarge,
                            color = Color.Black
                        )
                    }

                    // Barra de búsqueda
                    OutlinedTextField(
                        value = searchQuery,
                        onValueChange = { searchQuery = it },
                        label = { Text("Buscar recetas") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    )

                    // Cargando datos
                    if (isLoading) {
                        CircularProgressIndicator(modifier = Modifier.fillMaxSize())
                    } else if (error != null) {
                        Text(
                            text = "Error: $error",
                            color = Color.Red,
                            modifier = Modifier.fillMaxSize(),
                            style = MaterialTheme.typography.bodyLarge
                        )
                    } else {
                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            contentPadding = PaddingValues(8.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(recipes) { recipe ->
                                RecipeCard(recipe)
                            }
                        }
                    }
                }
            }
        }
    )
}


@Composable
fun RecipeCard(recipe: RandomRecipe) {
    var isFavorite by remember { mutableStateOf(false) } // Estado para el botón de favoritos

    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        shape = MaterialTheme.shapes.medium
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Imagen de la receta con bordes redondeados y proporción 1:1
            AsyncImage(
                model = recipe.image,
                contentDescription = recipe.title,
                modifier = Modifier
                    .size(100.dp)
                    .clip(MaterialTheme.shapes.medium)
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
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
                        imageVector = Icons.Filled.ThumbUp,
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
                        painter = painterResource(id = R.drawable.puntuacion),
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

            // Botón para guardar en favoritos
            IconButton(onClick = { isFavorite = !isFavorite }) {
                Icon(
                    imageVector = Icons.Filled.Favorite,
                    contentDescription = "Guardar en favoritos",
                    tint = if (isFavorite) Color.Red else Color.Gray
                )
            }

            // Botón para acceder a la preparación de la receta
            IconButton(onClick = {
                // Acción para navegar a la pantalla de preparación de la receta
            }) {
                Icon(
                    painter = painterResource(id = R.drawable.preparation), // Usa un icono que represente preparació
                    contentDescription = "Puntuacion",
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}
