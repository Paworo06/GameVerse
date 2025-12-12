package com.paworo06.gameverse.view.profile

import androidx.compose.foundation.Image
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.paworo06.gameverse.R
import com.paworo06.gameverse.ui.theme.GameVerseTheme // Usamos tu tema para el Preview

// --- Constantes de Color (Ajusta estos colores si tienes nombres específicos en Color.kt) ---
// Simulación de colores oscuros si no están definidos globalmente.
val PurplePrimary = Color(0xFF8C30E8)
val BackgroundDark = Color(0xFF191121)
val BackgroundSurface = Color(0xFF282038)
val TextGray = Color(0xFFAAAAAA)
val DividerColor = Color(0xFF222222)

/**
 * Composable principal que representa toda la pantalla de Perfil.
 * Solo contiene la vista (estructura UI) sin lógica de estado ni eventos.
 */
// Dentro de ProfileScreen()
@Composable
fun ProfileScreen(
    onNavigateToLogin: () -> Unit
) {
    // ... (código LazyColumn)

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundDark)
            .padding(horizontal = 16.dp)
    ) {
        item {
            // 1. Encabezado (Título y Cerrar Sesión)
            ProfileHeader(onLogoutClicked = onNavigateToLogin)

            Spacer(modifier = Modifier.height(24.dp))

            // 2. Información del Usuario
            ProfileUserInfoSection(
                name = "GamerXtreme",
                email = "gamer.xtreme@email.com"
            )

            // 3. Divisor
            Divider(color = DividerColor, thickness = 1.dp, modifier = Modifier.padding(vertical = 16.dp))

            // 4. Sección Mi Cuenta
            ProfileSectionTitle("Mi Cuenta")

            // REEMPLAZO 1: Usando tu imagen custom
            ProfileOptionItem(
                // Usa el ID del recurso que quieres poner.
                // Asegúrate de que este drawable exista en R.drawable.
                iconResId = R.drawable.usuario_info,
                title = "Información de la Cuenta"
            )

            // REEMPLAZO 2: Usando tu imagen custom
            ProfileOptionItem(
                // Usa el ID del recurso que quieres poner.
                iconResId = R.drawable.lista_deseos,
                title = "Lista de Deseos"
            )

            // 7. Divisor
            Divider(color = DividerColor, thickness = 1.dp, modifier = Modifier.padding(vertical = 16.dp))

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

// ----------------------------------------------------------------------------------
// --- COMPOSABLES AUXILIARES ---
// ----------------------------------------------------------------------------------

@Composable
fun ProfileHeader(
    onLogoutClicked: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Título "Perfil"
        Text(
            text = "Perfil",
            color = Color.White,
            fontSize = 24.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.weight(1f)
        )
        // Botón "Cerrar Sesión" (Solo vista, sin onClick real)
        Text(
            text = "Cerrar Sesión",
            color = PurplePrimary,
            fontSize = 16.sp,
            modifier = Modifier.clickable {
                onLogoutClicked() }
        )
    }
}

@Composable
fun ProfileUserInfoSection(name: String, email: String) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Avatar
        Box(
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape)
                .background(BackgroundSurface),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource( id = R.drawable.usuario ),
                contentDescription = "Avatar",
                tint = Color.White,
                modifier = Modifier.size(60.dp)
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Nombre
        Text(
            text = name,
            color = Color.White,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(4.dp))

        // Email
        Text(
            text = email,
            color = TextGray,
            fontSize = 14.sp
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Botón Editar Perfil
        Button(
            onClick = { /* Sin funcionalidad */ },
            colors = ButtonDefaults.buttonColors(containerColor = PurplePrimary),
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
        ) {
            Text("Editar Perfil", color = Color.White, fontSize = 16.sp)
        }
    }
}

@Composable
fun ProfileSectionTitle(title: String) {
    Text(
        text = title,
        color = TextGray,
        fontSize = 16.sp,
        modifier = Modifier.padding(bottom = 8.dp)
    )
}

// Cambia ImageVector a Int y usa painterResource para cargar el drawable.
@Composable
fun ProfileOptionItem(iconResId: Int, title: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { /* Solo clic fantasma */ }
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Ícono
        Image( // Usamos Image en lugar de Icon
            painter = painterResource(id = iconResId),
            contentDescription = title,
            // Aplicamos un ColorFilter para que la imagen se muestre blanca (si es necesario)
            // Si el drawable ya tiene el color deseado, puedes omitir colorFilter.
            colorFilter = ColorFilter.tint(Color.White),
            modifier = Modifier.size(24.dp)
        )

        Spacer(modifier = Modifier.width(16.dp))

        // Título
        Text(
            text = title,
            color = Color.White,
            fontSize = 18.sp,
            modifier = Modifier.weight(1f)
        )

        // Flecha derecha (usando el recurso existente)
        Image(
            painter = painterResource(id = R.drawable.profile_chevron_right ),
            contentDescription = "Ir",
            modifier = Modifier.size(14.dp)
        )
    }
}

// --- PREVIEW ---
@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    GameVerseTheme {
        ProfileScreen(onNavigateToLogin = {})
    }
}