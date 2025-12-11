package com.paworo06.gameverse.view.navegation

// Importaciones necesarias
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.paworo06.gameverse.R
import com.paworo06.gameverse.ui.theme.GameVerseTheme

// --- Constantes de Color (Ajustadas a tu tema) ---
val PurplePrimary = Color(0xFF7A00FF)
val BackgroundDark = Color(0xFF282038)
val TextGray = Color(0xFFAAAAAA)

/**
 * Define cada destino de navegación.
 */
sealed class BottomNavItem(val route: String, val icon: Int, val label: String) {
    object Home : BottomNavItem("home_route", R.drawable.home_icon, "Inicio")
    object Explore : BottomNavItem("explore_route", R.drawable.explore_icon, "Explorar")
    // usamos estas rutas en el NavHost, deben coincidir.
    object Cart : BottomNavItem("cart_route", R.drawable.cart_icon, "Carrito")
    object Profile : BottomNavItem("profile_route", R.drawable.usuario_info, "Perfil")
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
 * @param selectedRoute La ruta actualmente seleccionada (obtenida del NavController).
 * @param onItemSelected Función de callback que ejecuta la navegación en la MainActivity.
 */
@Composable
fun BottomNavigationBar(
    selectedRoute: String?, // Se acepta null si aún no hay destino
    onItemSelected: (String) -> Unit
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
                    onSelect = {
                        // Al hacer clic, se llama a la función pasada por la MainActivity
                        onItemSelected(item.route)
                    }
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
    val color = if (isSelected) PurplePrimary else TextGray

    // Contenedor del ítem
    Column(
        modifier = Modifier
            .clickable(onClick = onSelect)
            .padding(vertical = 4.dp, horizontal = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Icono
        Image(
            painter = painterResource(id = item.icon),
            contentDescription = item.label,
            // Aplicamos el tinte al icono
            colorFilter = ColorFilter.tint(color),
            modifier = Modifier.size(24.dp)
        )

        Spacer(modifier = Modifier.height(4.dp))

        // Etiqueta de texto
        Text(
            text = item.label,
            color = color, // El color del texto también cambia
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(BackgroundDark),
            verticalArrangement = Arrangement.Bottom
        ) {
            // Ejemplo: simulamos que el destino 'Home' está seleccionado
            BottomNavigationBar(
                selectedRoute = BottomNavItem.Home.route,
                onItemSelected = { /* No hay navegación real en Preview */ }
            )
        }
    }
}