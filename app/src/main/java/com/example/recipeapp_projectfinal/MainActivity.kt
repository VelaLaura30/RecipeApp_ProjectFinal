package com.example.recipeapp_projectfinal

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.recipeapp_projectfinal.composables.LoginScreen
import com.example.recipeapp_projectfinal.composables.MainScreen
import com.example.recipeapp_projectfinal.composables.RegisterScreen
import com.example.recipeapp_projectfinal.ui.theme.RecipeApp_ProjectFinalTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RecipeApp_ProjectFinalTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    // Setup del NavController y NavHost
                    RecipeAppNavHost()
                }
            }
        }
    }
}

@Composable
fun RecipeAppNavHost() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            LoginScreen(
                onLoginClick = {
                    navController.navigate("recipes") // Cuando inicie sesión, va a la pantalla de recetas
                },
                onNavigateToRegister = {
                    navController.navigate("register") // Navegar a la pantalla de registro
                }
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