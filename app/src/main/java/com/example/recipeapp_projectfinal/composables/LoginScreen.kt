package com.example.recipeapp_projectfinal.composables

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.text.font.FontWeight
import com.example.recipeapp_projectfinal.R
import com.example.recipeapp_projectfinal.data.viewmodel.LoginViewModel
import com.example.recipeapp_projectfinal.db.UserDao
import kotlinx.coroutines.withContext

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(onLoginClick: (Boolean) -> Unit, onNavigateToRegister: () -> Unit, loginViewModel: LoginViewModel) {
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val context = LocalContext.current


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
                    .padding(16.dp)
                    .align(Alignment.Center),
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
                    text = "Bienvenido a Recetas Deliciosas",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    color = Color.Black
                )

                Spacer(modifier = Modifier.height(8.dp))


                Text(
                    text = "Comparte, descubre y guarda tus recetas favoritas.",
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center,
                    color = Color.Black.copy(alpha = 0.7f)
                )

                Spacer(modifier = Modifier.height(24.dp))


                OutlinedTextField(
                    value = email.value,
                    onValueChange = { email.value = it },
                    label = { Text("Correo Electrónico") },
                    leadingIcon = {
                        Icon(Icons.Filled.Email, contentDescription = "Email Icon")
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        containerColor = Color.White.copy(alpha = 0.8f)
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))


                OutlinedTextField(
                    value = password.value,
                    onValueChange = { password.value = it },
                    label = { Text("Contraseña") },
                    leadingIcon = {
                        Icon(Icons.Rounded.Lock, contentDescription = "Password Icon")
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    modifier = Modifier.fillMaxWidth(),
                    visualTransformation = PasswordVisualTransformation(),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        containerColor = Color.White.copy(alpha = 0.8f)
                    )
                )

                Spacer(modifier = Modifier.height(24.dp))

                if (loginViewModel.errorMessage.isNotEmpty()) {
                    Text(
                        text = loginViewModel.errorMessage,
                        color = Color.Red,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(8.dp)
                    )
                }


                Button(
                    onClick = {
                        loginViewModel.authenticate(context, email.value, password.value)
                        if (loginViewModel.isAuthenticated) {
                            onLoginClick(true)
                        }
                    },
                    modifier = Modifier.fillMaxWidth().height(48.dp)
                ) {
                    Text(text = "Iniciar Sesión")
                }

                Spacer(modifier = Modifier.height(16.dp))


                Text(
                    text = "¿No tienes una cuenta? Regístrate",
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.clickable {
                        onNavigateToRegister()
                    }
                )
            }
        }
    }
}
