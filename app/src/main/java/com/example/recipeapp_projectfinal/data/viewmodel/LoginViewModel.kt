package com.example.recipeapp_projectfinal.data.viewmodel

import android.content.Context
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

    fun authenticate(context: Context, email: String, password: String) {

        viewModelScope.launch {
            try {
                val user = withContext(Dispatchers.IO) {
                    userDao.getUserByEmail(email)
                }


                if (user != null && user.password == password) {
                    isAuthenticated = true
                    saveUserName(context, user.name)
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

    private fun saveUserName(context: Context, userName: String) {
        val sharedPreferences = context.getSharedPreferences("UserPreferences", Context.MODE_PRIVATE)
        with(sharedPreferences.edit()) {
            putString("user_name", userName)
            apply()
        }
    }

    // Función para obtener el nombre del usuario desde SharedPreferences
    fun getUserName(context: Context): String {
        val sharedPreferences = context.getSharedPreferences("UserPreferences", Context.MODE_PRIVATE)
        return sharedPreferences.getString("user_name", "Usuario desconocido") ?: "Usuario desconocido"
    }

    fun logAllUsers() {
        viewModelScope.launch {
            val users = withContext(Dispatchers.IO) {
                userDao.getAllUsers()
            }
            Log.d("DatabaseLog", "Usuarios en la base de datos: $users")
        }
    }
}
