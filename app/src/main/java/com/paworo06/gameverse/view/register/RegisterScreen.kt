package com.paworo06.gameverse.view.register

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.input.VisualTransformation
import com.paworo06.gameverse.R

// --- COLORES REUTILIZADOS DEL DISEÑO ANTERIOR ---
val PrimaryDarkBackground = Color(0xFF191121)
val PrimaryActionButton = Color(0xFF8C30E8)
val TextLight = Color.White
val TextMuted = Color(0xFFCCCCCC)
val InputFieldBackground = Color(0xFF282038)

@Composable
fun SignupScreen() {
    // --- 1. ESTADOS PARA LOS NUEVOS CAMPOS ---
    var username by remember { mutableStateOf("") } // NUEVO: Nombre de Usuario
    var email by remember { mutableStateOf("") }       // NUEVO: Correo Electrónico
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") } // NUEVO: Confirmar Contraseña

    // Estado para mostrar errores de validación (ej. contraseñas no coinciden)
    var errorText by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(PrimaryDarkBackground)
            .padding(24.dp)
            .padding(top = 24.dp) // Ajuste para que el contenido no quede tan pegado al borde
            .systemBarsPadding(), // Maneja el relleno de la barra de estado
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // El Scroll es importante ya que tenemos más campos
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f), // Permite que la columna principal tome espacio restante
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // --- 1. Logo/Marca "Gameverse" ---
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 40.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.login_logo ),
                    contentDescription = "GameVerse Logo",
                    modifier = Modifier.size(32.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "GameVerse",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextLight
                )
            }


            // Título de la Pantalla
            Text(
                text = "Crear Cuenta",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = TextLight,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 32.dp)
            )

            // --- CAMPO NUEVO: Nombre de Usuario ---
            LoginInputField(
                value = username,
                onValueChange = { username = it },
                label = "Nombre de Usuario",
                placeholder = "Elige tu nombre de usuario"
            )

            Spacer(modifier = Modifier.height(16.dp))

            // --- CAMPO NUEVO: Correo Electrónico ---
            LoginInputField(
                value = email,
                onValueChange = { email = it },
                label = "Correo Electrónico",
                placeholder = "Escribe tu correo electrónico"
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Campo de Contraseña
            LoginInputField(
                value = password,
                onValueChange = { password = it },
                label = "Contraseña",
                placeholder = "Crea tu contraseña",
                isPassword = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            // --- CAMPO NUEVO: Confirmar Contraseña ---
            LoginInputField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                label = "Confirmar Contraseña",
                placeholder = "Repite tu contraseña",
                isPassword = true
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Mostrar mensaje de error si existe
            errorText?.let {
                Text(
                    text = it,
                    color = Color.Red,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Botón de REGISTRO
            Button(
                onClick = {
                    // Lógica de validación
                    if (password != confirmPassword) {
                        errorText = "Las contraseñas no coinciden."
                    } else if (username.isBlank() || email.isBlank() || password.isBlank()) {
                        errorText = "Todos los campos son obligatorios."
                    } else {
                        errorText = null // Limpiar el error si todo está bien
                        println("Intento de Registro con Usuario: $username, Correo: $email")
                        // Aquí llamarías a tu ViewModel para el registro
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = PrimaryActionButton),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(10.dp)
            ) {
                Text("Registrarse", color = TextLight, fontWeight = FontWeight.SemiBold)
            }
        } // Fin de Column principal (para el Scroll/espacio)

        // Enlace: ¿Ya tienes una cuenta? Inicia Sesión (AL PIE)
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        ) {
            Text(
                text = "¿Ya tienes una cuenta? ",
                color = TextMuted,
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Inicia Sesión",
                color = PrimaryActionButton,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.clickable { println("Ir a Login") }
            )
        }
    }
}

// **NOTA:** El componente LoginInputField no necesita cambios, se reutiliza perfectamente.

// --- Componente Auxiliar (Copiado de tu código para completitud) ---
@Composable
fun LoginInputField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    placeholder: String,
    isPassword: Boolean = false
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = label,
            color = TextLight,
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(bottom = 4.dp)
        )

        TextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = { Text(placeholder, color = TextMuted) },
            singleLine = true,
            visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,

            colors = TextFieldDefaults.colors(
                focusedContainerColor = InputFieldBackground,
                unfocusedContainerColor = InputFieldBackground,
                disabledContainerColor = InputFieldBackground,
                cursorColor = PrimaryActionButton,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                focusedTextColor = TextLight,
                unfocusedTextColor = TextLight
            ),
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.fillMaxWidth()
        )
    }
}


// --- Preview (Asegúrate de que el R.drawable.login_logo exista) ---
@Preview(showBackground = true)
@Composable
fun PreviewSignupScreen() {
    MaterialTheme(colorScheme = darkColorScheme(
        background = PrimaryDarkBackground,
        onBackground = TextLight,
        primary = PrimaryActionButton
    )) {
        // En un entorno de desarrollo, asegúrate de que R.drawable.login_logo exista.
        SignupScreen()
    }
}