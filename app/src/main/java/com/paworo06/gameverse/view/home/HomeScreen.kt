package com.paworo06.gameverse.view.home

import com.paworo06.gameverse.data.model.Game
import com.paworo06.gameverse.data.logic.GameRepository
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.paworo06.gameverse.ui.theme.GameVerseTheme

// --- Constantes de Color (Reutilizando las de tu tema oscuro) ---
val PurplePrimary = Color(0xFF7A00FF)
val BackgroundDark = Color(0xFF282038)
val BackgroundSurface = Color(0xFF25133D)
val TextGray = Color(0xFFAAAAAA)

/**
 * Composable principal de la pantalla de Inicio.
 */
@Composable
fun HomeScreen(
    // Inyectamos GameRepository. Por defecto usamos una nueva instancia si no se provee.
    gameRepository: GameRepository = GameRepository()
) {
    // 1. Obtener los datos del repositorio
    val allGames = gameRepository.getAllGames()

    // Simulación: Tomamos los primeros 3 para destacados
    val featuredGames = allGames.take(3)
    // Para recientes mostramos TODOS los juegos (así se ven los 5 que tienes)
    val recentGames = allGames 

    // LazyColumn para toda la pantalla
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundDark),
        contentPadding = PaddingValues(bottom = 16.dp)
    ) {
        item {
            // 1. Encabezado
            HomeHeader()

            Spacer(modifier = Modifier.height(24.dp))

            // 3. Título de Destacados
            SectionTitle("Juegos Destacados")

            // 4. Carrusel Horizontal
            FeaturedGamesRow(games = featuredGames)

            Spacer(modifier = Modifier.height(24.dp))

            // 5. Título de Recientes
            SectionTitle("Catálogo Completo") // Cambié el título para reflejar que son todos
        }

        // 6. Lista Vertical de Juegos
        items(recentGames, key = { it.id }) { game ->
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
fun FeaturedGamesRow(games: List<Game>) {
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
fun FeaturedGameCard(game: Game) {
    Card(
        modifier = Modifier
            .width(200.dp)
            .height(410.dp)
            .clickable { /* Acción de ver detalle */ },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = BackgroundSurface),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Image( 
                painter = painterResource(id = game.imageRes),
                contentDescription = game.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(240.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Column(modifier = Modifier.padding(horizontal = 12.dp)) {
                Text(
                    text = game.name, 
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = game.desc, 
                    color = TextGray,
                    fontSize = 12.sp,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.weight(1f)) // Empuja el precio hacia abajo
                Text(
                    text = "€${game.price}",
                    color = PurplePrimary,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(bottom = 12.dp)
                )
            }
        }
    }
}

@Composable
fun RecentGameItem(game: Game) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { /* Acción de ver detalle */ }
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Imagen real en la lista
        Image(
            painter = painterResource(id = game.imageRes),
            contentDescription = game.name,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(60.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(BackgroundSurface)
        )

        Spacer(modifier = Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = game.name,
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = game.desc,
                color = TextGray,
                fontSize = 12.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }

        // Precio a la derecha
        Text(
            text = "€${game.price}",
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
        Box(modifier = Modifier.fillMaxSize().background(BackgroundDark)) {
            HomeScreen()
        }
    }
}
