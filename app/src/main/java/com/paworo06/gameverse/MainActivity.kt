package com.paworo06.gameverse

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.paworo06.gameverse.ui.theme.GameVerseTheme
import com.paworo06.gameverse.view.profile.ProfileScreen
import com.paworo06.gameverse.view.cart.CartScreen
import com.paworo06.gameverse.view.navegation.BottomNavigationBar

// Define las posibles pantallas como un Sealed Class para seguridad de tipos
sealed class ScreenState(val route: String) {
    object Profile : ScreenState("profile_route")
    object Cart : ScreenState("cart_route")
    object Home : ScreenState("home_route")
    object Explore : ScreenState("explore_route")
}

// Color de fondo oscuro para el Scaffold
val BackgroundDark = Color(0xFF121212)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GameVerseTheme {
                MainAppStructure()
            }
        }
    }
}

@Composable
fun MainAppStructure() {
    // 1. Estado de la pantalla activa
    var currentScreen by remember { mutableStateOf(ScreenState.Cart.route) }

    Scaffold(
        containerColor = BackgroundDark,
        bottomBar = {
            BottomNavigationBar(
                selectedRoute = currentScreen,
                // 2. Al hacer clic, simplemente cambiamos la variable de estado
                onItemSelected = { route ->
                    currentScreen = route // Esto redibuja el Composable
                }
            )
        }
    ) { paddingValues ->
        Box(modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize()
        ) {
            // 3. Lógica de cambio de pantalla basada en el estado
            when (currentScreen) {
                ScreenState.Profile.route -> ProfileScreen()
                ScreenState.Cart.route -> CartScreen()
                // Puedes usar un placeholder o las pantallas reales aquí
                ScreenState.Home.route -> PlaceholderScreen(name = "Inicio")
                ScreenState.Explore.route -> PlaceholderScreen(name = "Explorar")
            }
        }
    }
}

// *** IMPORTANTE: Necesitas las rutas correctas aquí ***
// Función PlaceholderScreen (para que compile si no tienes las vistas)
@Composable
fun PlaceholderScreen(name: String) {
    Box(modifier = Modifier.fillMaxSize().background(BackgroundDark)) {
        androidx.compose.material3.Text(
            text = "Contenido de la pantalla: $name (Ruta simple)",
            color = Color.White,
            modifier = Modifier.padding(16.dp)
        )
    }
}