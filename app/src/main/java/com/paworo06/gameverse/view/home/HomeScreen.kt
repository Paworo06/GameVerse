package com.paworo06.gameverse.view.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.paworo06.gameverse.R
import com.paworo06.gameverse.ui.theme.GameVerseTheme

// --- Constantes de Color (Reutilizando las de tu tema oscuro) ---
val PurplePrimary = Color(0xFF7A00FF)
val BackgroundDark = Color(0xFF121212)
val BackgroundSurface = Color(0xFF1C1C1C)
val TextGray = Color(0xFFAAAAAA)

/**
 * Clase de datos simple para simular los juegos (idealmente vendría de data/model/Game.kt)
 */
data class MockGame(val id: Int, val title: String, val genre: String, val price: String)

// Datos de ejemplo
private val featuredGames = listOf(
    MockGame(1, "Cyber Saga 2077", "RPG", "$59.99"),
    MockGame(2, "Galaxy Blitz", "Shooter", "$49.99"),
    MockGame(3, "Fantasy Realms", "Strategy", "$39.99"),
)

private val recentGames = listOf(
    MockGame(4, "Ninja Clash", "Action", "$19.99"),
    MockGame(5, "Space Racer", "Racing", "$29.99"),
    MockGame(6, "Zombie Defense", "Survival", "$9.99"),
)


/**
 * Composable principal de la pantalla de Inicio.
 */
@Composable
fun HomeScreen() {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundDark),
        contentPadding = PaddingValues(bottom = 16.dp)
    ) {
        item {
            // 1. Encabezado
            HomeHeader()

            // 2. Barra de Búsqueda
            SearchInput()

            Spacer(modifier = Modifier.height(24.dp))

            // 3. Título de Destacados
            SectionTitle("Juegos Destacados")

            // 4. Carrusel Horizontal
            FeaturedGamesRow(games = featuredGames)

            Spacer(modifier = Modifier.height(24.dp))

            // 5. Título de Recientes
            SectionTitle("Novedades y Recientes")
        }

        // 6. Lista Vertical de Juegos Recientes
        items(recentGames) { game ->
            RecentGameItem(game = game)
        }
    }
}

// ----------------------------------------------------------------------------------
// --- COMPOSABLES AUXILIARES ---
// ----------------------------------------------------------------------------------

@Composable
fun HomeHeader() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 24.dp, start = 16.dp, end = 16.dp, bottom = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = "¡Hola, GamerXtreme!",
                color = Color.White,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Listo para jugar hoy?",
                color = TextGray,
                fontSize = 14.sp
            )
        }
        // Icono de Notificación (opcional)
        Icon(
            imageVector = Icons.Default.Notifications,
            contentDescription = "Notificaciones",
            tint = Color.White,
            modifier = Modifier
                .size(28.dp)
                .clickable { /* Acción de notificación */ }
        )
    }
}

@Composable
fun SearchInput() {
    // Barra de búsqueda con estilo oscuro
    OutlinedTextField(
        value = "", // Valor fijo, sin funcionalidad de estado aquí
        onValueChange = { /* Sin funcionalidad */ },
        placeholder = { Text("Buscar juegos, géneros o consolas...", color = TextGray) },
        leadingIcon = {
            Icon(Icons.Default.Search, contentDescription = "Buscar", tint = TextGray)
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(12.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = PurplePrimary,
            unfocusedBorderColor = BackgroundSurface,
            cursorColor = PurplePrimary,
            focusedContainerColor = BackgroundSurface,
            unfocusedContainerColor = BackgroundSurface,
            focusedTextColor = Color.White,
            unfocusedTextColor = Color.White
        )
    )
}

@Composable
fun SectionTitle(title: String) {
    Text(
        text = title,
        color = Color.White,
        fontSize = 20.sp,
        fontWeight = FontWeight.SemiBold,
        modifier = Modifier.padding(start = 16.dp, bottom = 8.dp)
    )
}

@Composable
fun FeaturedGamesRow(games: List<MockGame>) {
    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(games) { game ->
            FeaturedGameCard(game = game)
        }
    }
}

@Composable
fun FeaturedGameCard(game: MockGame) {
    Card(
        modifier = Modifier
            .width(200.dp)
            .height(250.dp)
            .clickable { /* Acción de ver detalle */ },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = BackgroundSurface),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            // Placeholder de imagen del juego (simulada con un Box)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp)
                    .background(PurplePrimary.copy(alpha = 0.5f)),
                contentAlignment = Alignment.Center
            ) {
                Text(game.title, color = Color.White, fontWeight = FontWeight.SemiBold)
            }
            Spacer(modifier = Modifier.height(8.dp))
            Column(modifier = Modifier.padding(horizontal = 12.dp)) {
                Text(
                    text = game.title,
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = game.genre,
                    color = TextGray,
                    fontSize = 12.sp
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = game.price,
                    color = PurplePrimary,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}

@Composable
fun RecentGameItem(game: MockGame) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { /* Acción de ver detalle */ }
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Placeholder de imagen (simulada)
        Box(
            modifier = Modifier
                .size(60.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(BackgroundSurface),
            contentAlignment = Alignment.Center
        ) {
            Text("IMG", color = TextGray)
        }

        Spacer(modifier = Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = game.title,
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = game.genre,
                color = TextGray,
                fontSize = 12.sp
            )
        }

        // Precio a la derecha
        Text(
            text = game.price,
            color = PurplePrimary,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
    }
}


// --- PREVIEW ---
@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    GameVerseTheme {
        // Usa un Box para simular el área segura del Scaffold (sin la barra inferior)
        Box(modifier = Modifier.fillMaxSize().background(BackgroundDark)) {
            HomeScreen()
        }
    }
}