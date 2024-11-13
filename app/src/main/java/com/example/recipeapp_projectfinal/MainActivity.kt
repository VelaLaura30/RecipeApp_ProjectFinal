package com.example.recipeapp_projectfinal

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.recipeapp_projectfinal.composables.LoginScreen
import com.example.recipeapp_projectfinal.composables.MainScreen
import com.example.recipeapp_projectfinal.composables.RegisterScreen
import com.example.recipeapp_projectfinal.data.viewmodel.LoginViewModel
import com.example.recipeapp_projectfinal.data.viewmodel.LoginViewModelFactory
import com.example.recipeapp_projectfinal.db.DatabaseProvider
import com.example.recipeapp_projectfinal.ui.theme.RecipeApp_ProjectFinalTheme
//import com.google.firebase.Firebase
//import com.google.firebase.firestore.firestore



class MainActivity : ComponentActivity() {
    // Usando viewModels con un factory para obtener el LoginViewModel
    private val loginViewModel: LoginViewModel by viewModels {
        LoginViewModelFactory(DatabaseProvider.getInstance(applicationContext).userDao())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RecipeApp_ProjectFinalTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    // Pasa el loginViewModel a RecipeAppNavHost
                    RecipeAppNavHost(loginViewModel = loginViewModel)
                }
            }
        }
        //val db = Firebase.firestore
        loginViewModel.logAllUsers()
    }
}

@Composable
fun RecipeAppNavHost(loginViewModel: LoginViewModel) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            LoginScreen(
                onLoginClick = {
                    navController.navigate("recipes") // Cuando inicie sesión, va a la pantalla de recetas
                },
                onNavigateToRegister = {
                    navController.navigate("register") // Navegar a la pantalla de registro
                },
                loginViewModel = loginViewModel // Pasa el LoginViewModel aquí
            )
        }
        composable("register") {
            RegisterScreen(onRegisterClick = {
                navController.navigate("login") // Volver a Login después del registro
            })
        }
        composable("recipes") {
            MainScreen() // Aquí va la pantalla de recetas
        }
    }
}