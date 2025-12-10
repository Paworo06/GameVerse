package com.paworo06.gameverse.view.navegation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter.Companion.tint
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.paworo06.gameverse.R
import com.paworo06.gameverse.ui.theme.GameVerseTheme

// --- Constantes de Color (Ajustadas a tu tema) ---
val PurplePrimary = Color(0xFF7A00FF)
val BackgroundDark = Color(0xFF282038) // Color de fondo uniforme
val TextGray = Color(0xFFAAAAAA)

/**
 * Define cada destino de navegación.
 */
sealed class BottomNavItem(val route: String, val icon: Int, val label: String) {
    object Home : BottomNavItem("home", R.drawable.home_icon, "Inicio")
    object Explore : BottomNavItem("explore", R.drawable.explore_icon, "Explorar")
    object Cart : BottomNavItem("CartScreen", R.drawable.cart_icon, "Carrito")
    object Profile : BottomNavItem("ProfileScreen", R.drawable.usuario_info, "Perfil")
}

// Lista de ítems para mostrar en la barra
val items = listOf(
    BottomNavItem.Home,
    BottomNavItem.Explore,
    BottomNavItem.Cart,
    BottomNavItem.Profile
)

/**
 * Composable principal de la Barra de Navegación Inferior.
 *
 * @param selectedRoute La ruta actualmente seleccionada (simula el estado de navegación).
 * @param onItemSelected Función de callback al seleccionar un nuevo ítem.
 */
@Composable
fun BottomNavigationBar(
    selectedRoute: String = BottomNavItem.Profile.route, // Por defecto, Perfil está activo
    onItemSelected: (String) -> Unit = {} // Sin funcionalidad real, solo para la vista
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(BackgroundDark)
            .height(70.dp)
            .padding(horizontal = 8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            items.forEach { item ->
                val isSelected = item.route == selectedRoute
                BottomNavigationItem(
                    item = item,
                    isSelected = isSelected,
                    onSelect = { onItemSelected(item.route) }
                )
            }
        }
    }
}

/**
 * Representa un solo ítem dentro de la barra de navegación.
 */
@Composable
fun BottomNavigationItem(
    item: BottomNavItem,
    isSelected: Boolean,
    onSelect: () -> Unit
) {
    val iconColor = if (isSelected) PurplePrimary else TextGray
    val textColor = if (isSelected) PurplePrimary else TextGray

    // Contenedor del ítem
    Column(
        modifier = Modifier
            .clickable(onClick = onSelect)
            .padding(vertical = 4.dp, horizontal = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Icono (usamos Image para cargar PNG/JPG y aplicar el color vía tint si es posible)
        Image(
            painter = painterResource(id = item.icon),
            contentDescription = item.label,
            // Aplicamos un tinte para simular el cambio de color al seleccionar
            colorFilter = tint(iconColor),
            modifier = Modifier.size(24.dp)
        )

        Spacer(modifier = Modifier.height(4.dp))

        // Etiqueta de texto
        Text(
            text = item.label,
            color = textColor,
            fontSize = 12.sp,
            fontWeight = FontWeight.Normal
        )
    }
}

// --- PREVIEW ---
@Preview(showBackground = true)
@Composable
fun BottomNavigationBarPreview() {
    GameVerseTheme {
        // Muestra la barra con el perfil seleccionado
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(BackgroundDark),
            verticalArrangement = Arrangement.Bottom
        ) {
            // Este es el composable que necesitas usar en tu Scaffold
            BottomNavigationBar(selectedRoute = BottomNavItem.Profile.route)
        }
    }
}