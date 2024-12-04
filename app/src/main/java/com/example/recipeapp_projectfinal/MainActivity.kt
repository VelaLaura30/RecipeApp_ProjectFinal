package com.example.recipeapp_projectfinal

import NewRecipeViewModel
import RandomRecipeScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.recipeapp_projectfinal.composables.AddRecipeScreen
import com.example.recipeapp_projectfinal.composables.AllRecipesScreen
//import com.example.recipeapp_projectfinal.composables.FavoriteRecipesScreen
import com.example.recipeapp_projectfinal.composables.LoginScreen
import com.example.recipeapp_projectfinal.composables.MainScreen
import com.example.recipeapp_projectfinal.composables.PreparationScreen
import com.example.recipeapp_projectfinal.composables.RegisterScreen
import com.example.recipeapp_projectfinal.data.viewmodel.LoginViewModel
import com.example.recipeapp_projectfinal.data.viewmodel.LoginViewModelFactory
import com.example.recipeapp_projectfinal.data.viewmodel.NewRecipeViewModelFactory
import com.example.recipeapp_projectfinal.data.viewmodel.RecipeRandomViewModel
import com.example.recipeapp_projectfinal.db.DatabaseProvider
import com.example.recipeapp_projectfinal.ui.theme.RecipeApp_ProjectFinalTheme




class MainActivity : ComponentActivity() {

    private val loginViewModel: LoginViewModel by viewModels {
        LoginViewModelFactory(DatabaseProvider.getInstance(applicationContext).userDao())
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RecipeApp_ProjectFinalTheme {
                Surface(color = MaterialTheme.colorScheme.background) {

                    RecipeAppNavHost(loginViewModel = loginViewModel)
                }
            }
        }

        loginViewModel.logAllUsers()
    }
}

@Composable
fun RecipeAppNavHost(loginViewModel: LoginViewModel) {
    val navController = rememberNavController()

    // Initialize the NewRecipeViewModel with the correct factory
    val newRecipeViewModel: NewRecipeViewModel = viewModel(
        factory = NewRecipeViewModelFactory(DatabaseProvider.getInstance(LocalContext.current).newRecipeDao())
    )

    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            LoginScreen(
                onLoginClick = {
                    navController.navigate("recipes")
                },
                onNavigateToRegister = {
                    navController.navigate("register")
                },
                loginViewModel = loginViewModel
            )
        }
        composable("register") {
            RegisterScreen(onRegisterClick = {
                navController.navigate("login")
            })
        }
        composable("recipes") {
            MainScreen(navController)
        }
        composable("preparation/{recipeId}") { backStackEntry ->
            val recipeId = backStackEntry.arguments?.getString("recipeId")?.toIntOrNull()
            val viewModel: RecipeRandomViewModel = viewModel()

            LaunchedEffect(recipeId) {
                if (recipeId != null) {
                    viewModel.fetchRecipeById(recipeId)
                }
            }

            val recipe = viewModel.recipeDetails.value

            if (recipe != null) {
                PreparationScreen(preparationRecipe = recipe)
            } else {
                Text("Cargando receta...", modifier = Modifier.fillMaxSize())
            }
        }
        composable("addRecipeScreen") {
            AddRecipeScreen(
                viewModel = newRecipeViewModel,
                onRecipeSaved = {
                    navController.navigate("allRecipesScreen") // Navigate after saving recipe
                }
            )
        }
        composable("allRecipesScreen") {
            AllRecipesScreen(viewModel = newRecipeViewModel)
        }
    }
}



