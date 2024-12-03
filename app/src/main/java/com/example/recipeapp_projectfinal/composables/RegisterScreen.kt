package com.example.recipeapp_projectfinal.composables

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import com.example.recipeapp_projectfinal.R
import com.example.recipeapp_projectfinal.db.DatabaseProvider
import com.example.recipeapp_projectfinal.db.User
import com.example.recipeapp_projectfinal.db.UserDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun RegisterScreen(onRegisterClick: () -> Unit) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    val db = DatabaseProvider.getInstance(context = LocalContext.current)
    val userDao = db.userDao()


    Scaffold { paddingValues ->
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            // Fondo de la imagen
            Image(
                painter = painterResource(id = R.drawable.fondorecetas), // Reemplaza con tu recurso de imagen de fondo
                contentDescription = "Fondo",
                modifier = Modifier.fillMaxSize(),
                alignment = Alignment.Center,
                alpha = 0.3f // Puedes ajustar la opacidad de la imagen de fondo
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                // Logo
                Image(
                    painter = painterResource(id = R.drawable.icon_app), // Reemplaza con tu recurso de logo
                    contentDescription = "Logo del sitio de recetas",
                    modifier = Modifier.size(120.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Título
                Text(
                    text = "Únete a Recetas Deliciosas",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Descripción
                Text(
                    text = "Crea una cuenta para compartir y descubrir recetas increíbles.",
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center,
                    color = Color.Black
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Campo de Nombre
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Nombre Completo") },
                    leadingIcon = {
                        Icon(Icons.Filled.Person, contentDescription = "User Icon")
                    },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Campo de Email
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Correo Electrónico") },
                    leadingIcon = {
                        Icon(Icons.Filled.Email, contentDescription = "Email Icon")
                    },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Campo de Contraseña
                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Contraseña") },
                    leadingIcon = {
                        Icon(Icons.Rounded.Lock, contentDescription = "Password Icon")
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    modifier = Modifier.fillMaxWidth(),
                    visualTransformation = PasswordVisualTransformation()
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Campo de Confirmar Contraseña
                OutlinedTextField(
                    value = confirmPassword,
                    onValueChange = { confirmPassword = it },
                    label = { Text("Confirmar Contraseña") },
                    leadingIcon = {
                        Icon(Icons.Rounded.Lock, contentDescription = "Password Icon")
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Botón de Registro
                Button(
                    onClick = {
                        // Validar que los campos no estén vacíos y que las contraseñas coincidan
                        if (name.isNotBlank() && email.isNotBlank() && password == confirmPassword) {
                            // Crear un objeto `User`
                            val user = User(name = name, email = email, password = password)

                            // Llamar a `registerUser` para guardar el usuario en la base de datos
                            registerUser(user, userDao) {
                                // Acción después de registrar, como navegar a otra pantalla
                                onRegisterClick()
                            }
                        } else {
                            Log.d("RegisterScreen", "Error: Campos vacíos o contraseñas no coinciden.")
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                ) {
                    Text(text = "Registrarse")
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Texto de inicio de sesión
                Text(
                    text = "¿Ya tienes una cuenta? Inicia sesión",
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.clickable {
                        onRegisterClick() // Navegar al login
                    }
                )
            }
        }

    }
    }

private fun registerUser(user: User, userDao: UserDao, onRegisterSuccess: () -> Unit) {
    GlobalScope.launch(Dispatchers.IO) {
        // Insertar usuario en la base de datos
        userDao.insertUser(user)
        Log.d("DatabaseLog", "Usuario registrado: $user")
        // Volver al hilo principal para mostrar mensaje de éxito
        withContext(Dispatchers.Main) {
            onRegisterSuccess()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RegisterScreenPreview() {
    RegisterScreen(onRegisterClick = { /* Acción de registro */ })
}
