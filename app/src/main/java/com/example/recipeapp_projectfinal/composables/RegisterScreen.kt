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

            Image(
                painter = painterResource(id = R.drawable.fondorecetas),
                contentDescription = "Fondo",
                modifier = Modifier.fillMaxSize(),
                alignment = Alignment.Center,
                alpha = 0.3f
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                Image(
                    painter = painterResource(id = R.drawable.icon_app),
                    contentDescription = "Logo del sitio de recetas",
                    modifier = Modifier.size(120.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))


                Text(
                    text = "Únete a Recetas Deliciosas",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(8.dp))


                Text(
                    text = "Crea una cuenta para compartir y descubrir recetas increíbles.",
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center,
                    color = Color.Black
                )

                Spacer(modifier = Modifier.height(24.dp))


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


                OutlinedTextField(
                    value = confirmPassword,
                    onValueChange = { confirmPassword = it },
                    label = { Text("Confirmar Contraseña") },
                    leadingIcon = {
                        Icon(Icons.Rounded.Lock, contentDescription = "Password Icon")
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    modifier = Modifier.fillMaxWidth(),
                    visualTransformation = PasswordVisualTransformation()
                )

                Spacer(modifier = Modifier.height(24.dp))


                Button(
                    onClick = {

                        if (name.isNotBlank() && email.isNotBlank() && password == confirmPassword) {

                            val user = User(name = name, email = email, password = password)

                            registerUser(user, userDao) {
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


                Text(
                    text = "¿Ya tienes una cuenta? Inicia sesión",
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.clickable {
                        onRegisterClick()
                    }
                )
            }
        }

    }
    }

private fun registerUser(user: User, userDao: UserDao, onRegisterSuccess: () -> Unit) {
    GlobalScope.launch(Dispatchers.IO) {

        userDao.insertUser(user)
        Log.d("DatabaseLog", "Usuario registrado: $user")
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
