package com.example.recipeapp_projectfinal.composables

import RandomRecipeScreen
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.example.recipeapp_projectfinal.data.viewmodel.RecipeRandomViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun MainScreen() {
    val viewModel: RecipeRandomViewModel = viewModel()

    LaunchedEffect(Unit) {
        viewModel.getRandomRecipe(5)
    }

    RandomRecipeScreen(viewModel = viewModel)
}