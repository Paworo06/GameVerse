package com.paworo06.gameverse.view.register

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.* // Necesitamos 'runtime' para el Composable, aunque no usemos 'remember'
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
fun SignupScreen(
    onNavigateToLogin: () -> Unit,
    onRegistrationSuccess: () -> Unit
) {
    // Definimos variables de estado dummy *LOCAMENTE* dentro de esta función
    // para que los TextFields puedan aceptar la entrada del usuario,
    // pero no guardarán el estado de forma persistente a nivel de ViewModel ni validarán nada.
    var currentUsernameValue by remember { mutableStateOf("") }
    var currentEmailValue by remember { mutableStateOf("") }
    var currentPasswordValue by remember { mutableStateOf("") }
    var currentConfirmPasswordValue by remember { mutableStateOf("") }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(PrimaryDarkBackground)
            .padding(24.dp)
            .padding(top = 24.dp)
            .systemBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // --- 1. Logo/Marca "Gameverse" ---
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 40.dp)
            ) {
                Image(
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
                text = "Crear Cuenta",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = TextLight,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 32.dp)
            )

            // --- Nombre de Usuario ---
            LoginInputField(
                value = currentUsernameValue,
                onValueChange = { currentUsernameValue = it }, // Permite escribir
                label = "Nombre de Usuario",
                placeholder = "Elige tu nombre de usuario"
            )

            Spacer(modifier = Modifier.height(16.dp))

            // --- Correo Electrónico ---
            LoginInputField(
                value = currentEmailValue,
                onValueChange = { currentEmailValue = it }, // Permite escribir
                label = "Correo Electrónico",
                placeholder = "Escribe tu correo electrónico"
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Campo de Contraseña
            LoginInputField(
                value = currentPasswordValue,
                onValueChange = { currentPasswordValue = it }, // Permite escribir
                label = "Contraseña",
                placeholder = "Crea tu contraseña",
                isPassword = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            // --- Confirmar Contraseña ---
            LoginInputField(
                value = currentConfirmPasswordValue,
                onValueChange = { currentConfirmPasswordValue = it }, // Permite escribir
                label = "Confirmar Contraseña",
                placeholder = "Repite tu contraseña",
                isPassword = true
            )

            Spacer(modifier = Modifier.height(30.dp))

            // Botón de REGISTRO
            Button(
                // onClick no hace nada real, solo imprime un mensaje
                onClick = {
                    println("Botón Registrarse presionado (Sin lógica)")
                    onRegistrationSuccess()
                },
                colors = ButtonDefaults.buttonColors(containerColor = PrimaryActionButton),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(10.dp)
            ) {
                Text("Registrarse", color = TextLight, fontWeight = FontWeight.SemiBold)
            }
        } // Fin de Column principal

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
                modifier = Modifier.clickable { onNavigateToLogin() }
            )
        }
    }
}

// --- Componente Auxiliar (MANTENIDO) ---
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
fun PreviewSignupScreen() {
    MaterialTheme(
        colorScheme = darkColorScheme(
            background = PrimaryDarkBackground,
            onBackground = TextLight,
            primary = PrimaryActionButton
        )
    ) {
        SignupScreen(
            onNavigateToLogin = {},
            onRegistrationSuccess = {}
        )
    }
}