package com.example.recipeapp_projectfinal.data.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipeapp_projectfinal.db.UserDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginViewModel(private val userDao: UserDao) : ViewModel() {

    var isAuthenticated: Boolean = false
    var errorMessage: String = ""

    fun authenticate(email: String, password: String) {
        // Lanzamos la tarea en un hilo de fondo
        viewModelScope.launch {
            try {
                val user = withContext(Dispatchers.IO) {
                    userDao.getUserByEmail(email) // Esta consulta ahora ocurre en un hilo de fondo
                }

                // Comprobamos si el usuario existe y la contraseña es correcta
                if (user != null && user.password == password) {
                    isAuthenticated = true
                } else {
                    errorMessage = "Invalid email or password"
                    isAuthenticated = false
                }
            } catch (e: Exception) {
                errorMessage = "Error occurred: ${e.message}"
                isAuthenticated = false
            }
        }
    }

    fun logAllUsers() {
        viewModelScope.launch {
            val users = withContext(Dispatchers.IO) {
                userDao.getAllUsers() // Suponiendo que tienes una función getAllUsers en UserDao
            }
            Log.d("DatabaseLog", "Usuarios en la base de datos: $users")
        }
    }
}
