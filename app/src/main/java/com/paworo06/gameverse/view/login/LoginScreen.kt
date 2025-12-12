package com.paworo06.gameverse.view.login

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
import androidx.compose.ui.res.painterResource // NECESARIO para cargar imágenes locales
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.input.VisualTransformation
import com.paworo06.gameverse.R // NECESARIO para acceder a R.drawable

// --- COLORES REUTILIZADOS DEL DISEÑO ANTERIOR ---
val PrimaryDarkBackground = Color(0xFF191121)
val PrimaryActionButton = Color(0xFF8C30E8)
val TextLight = Color.White
val TextMuted = Color(0xFFCCCCCC)
val InputFieldBackground = Color(0xFF282038)

@Composable
fun LoginScreen() {
    var usernameOrEmail by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(PrimaryDarkBackground)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(48.dp))

        // --- 1. Logo/Marca "Gameverse" (USANDO IMAGEN LOCAL) ---
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = 60.dp)
        ) {

            // Reemplazamos Icon por Image y usamos painterResource
            Image(
                // ** IMPORTANTE: Asume que tienes el archivo en res/drawable/gameverse_logo.png
                painter = painterResource(id = R.drawable.login_logo),
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
            text = "Iniciar Sesión",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = TextLight,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 32.dp)
        )

        // Campo de Usuario o Correo
        LoginInputField(
            value = usernameOrEmail,
            onValueChange = { usernameOrEmail = it },
            label = "Usuario o Correo",
            placeholder = "Escribe tu usuario o correo"
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Campo de Contraseña
        LoginInputField(
            value = password,
            onValueChange = { password = it },
            label = "Contraseña",
            placeholder = "Escribe tu contraseña",
            isPassword = true
        )

        Spacer(modifier = Modifier.height(30.dp))

        // Botón de LOGIN
        Button(
            onClick = { println("Intento de Login con $usernameOrEmail") },
            colors = ButtonDefaults.buttonColors(containerColor = PrimaryActionButton),
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(10.dp)
        ) {
            Text("Login", color = TextLight, fontWeight = FontWeight.SemiBold)
        }

        Spacer(modifier = Modifier.height(150.dp))

        // Enlace: ¿No tienes una cuenta? Regístrate
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "¿No tienes una cuenta? ",
                color = TextMuted,
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Regístrate",
                color = PrimaryActionButton,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.clickable { println("Ir a registro") }
            )
        }
    }
}

// Componente Auxiliar para Campos de Entrada
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


// --- Preview ---
@Preview(showBackground = true)
@Composable
fun PreviewLoginScreen() {

    MaterialTheme(colorScheme = darkColorScheme(
        background = PrimaryDarkBackground,
        onBackground = TextLight,
        primary = PrimaryActionButton
    )) {
        // Llama a LoginScreen, que usará la imagen gracias a la magia de la Preview
        LoginScreen()
    }
}