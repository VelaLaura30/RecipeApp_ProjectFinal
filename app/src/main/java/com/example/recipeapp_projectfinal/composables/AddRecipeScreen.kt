package com.example.recipeapp_projectfinal.composables

import NewRecipeViewModel
import android.content.Context
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.recipeapp_projectfinal.db.NewRecipe


@Composable
fun AddRecipeScreen(viewModel: NewRecipeViewModel, onRecipeSaved: () -> Unit) {
    val context = LocalContext.current
    val title = remember { mutableStateOf("") }
    val prepTime = remember { mutableStateOf("") }
    val ingredients = remember { mutableStateOf("") }
    val recipeUrl = remember { mutableStateOf("") }
    val imageUri = remember { mutableStateOf<Uri?>(null) }
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri -> imageUri.value = uri }
    )

    fun uriToByteArray(context: Context, uri: Uri): ByteArray {
        return context.contentResolver.openInputStream(uri)?.use { inputStream ->
            inputStream.readBytes()
        } ?: ByteArray(0)
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Agregar Nueva Receta", style = MaterialTheme.typography.titleLarge)

        OutlinedTextField(
            value = title.value,
            onValueChange = { title.value = it },
            label = { Text("Título de la receta") },
            modifier = Modifier.fillMaxWidth()
        )

        Button(onClick = { launcher.launch("image/*") }) {
            Text("Seleccionar Imagen")
        }

        imageUri.value?.let { uri ->
            Image(
                painter = rememberAsyncImagePainter(uri),
                contentDescription = "Imagen seleccionada",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )
        }

        OutlinedTextField(
            value = prepTime.value,
            onValueChange = { prepTime.value = it },
            label = { Text("Tiempo de preparación (en minutos)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = ingredients.value,
            onValueChange = { ingredients.value = it },
            label = { Text("Ingredientes (separados por comas)") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = recipeUrl.value,
            onValueChange = { recipeUrl.value = it },
            label = { Text("URL de la receta (opcional)") },
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {
                val imageBytes = imageUri.value?.let { uriToByteArray(context, it) }
                if (title.value.isNotEmpty() && prepTime.value.isNotEmpty() && ingredients.value.isNotEmpty() && imageBytes != null) {
                    val newRecipe = NewRecipe(
                        title = title.value,
                        image = imageBytes,
                        readyInMinutes = prepTime.value.toInt(),
                        ingredients = ingredients.value,
                        sourceUrl = recipeUrl.value,
                        creator = viewModel.getUserName(context)
                    )
                    viewModel.addRecipe(newRecipe)


                    onRecipeSaved()
                }
            },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text("Guardar Receta")
        }

    }
}
