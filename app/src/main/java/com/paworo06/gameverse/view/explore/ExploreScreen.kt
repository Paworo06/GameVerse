package com.paworo06.gameverse.view.explore

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
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

// Importaciones de tus modelos y repositorio
import com.paworo06.gameverse.R
import com.paworo06.gameverse.data.logic.GameRepository
import com.paworo06.gameverse.data.model.Game

// --- CONSTANTES DE COLOR ---
val PrimaryDarkBackground = Color(0xFF191121)
val SecondaryDark = Color(0xFF282038)
val PrimaryActionButton = Color(0xFF8C30E8)
val TextLight = Color.White
val TextMuted = Color(0xFFCCCCCC)
val HighlightButtonActive = Color(0xFF8C30E8)

// Opciones de ordenamiento (solo precio)
val sortOptionsPrice = listOf("Menor Precio", "Mayor Precio")


@Composable
fun ExploreScreen() {
    // --- ESTADOS ---
    var searchQuery by remember { mutableStateOf("") }
    var filtersVisible by remember { mutableStateOf(false) }
    var selectedSort by remember { mutableStateOf("Menor Precio") }
    // Asumimos un rango máximo de 100€ para el Slider de ejemplo
    var priceRange by remember { mutableStateOf(0f..100f) }

    // Lista original obtenida del repositorio
    val originalGames = remember { GameRepository().getAllGames() }

    // Lista filtrada: Recalculada cada vez que un estado (searchQuery, selectedSort, priceRange) cambia
    val filteredGames = remember(searchQuery, selectedSort, priceRange) {
        applyFiltersAndSearch(
            games = originalGames,
            searchQuery = searchQuery,
            sortOption = selectedSort,
            priceRange = priceRange
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(PrimaryDarkBackground)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            // --- Encabezado y Barra de Búsqueda ---
            Text(
                text = "Explorar",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                color = TextLight,
                modifier = Modifier.padding(top = 16.dp, bottom = 16.dp)
            )

            SearchAndFilterBar(
                searchQuery = searchQuery,
                onSearchChange = { searchQuery = it },
                onFilterClick = { filtersVisible = true }
            )

            Spacer(modifier = Modifier.height(24.dp))

            // --- Resultados de la Lista ---
            Text(
                text = "${filteredGames.size} juegos encontrados",
                color = TextMuted,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            LazyColumn(
                contentPadding = PaddingValues(bottom = 16.dp)
            ) {
                // Renderizamos la lista filtrada
                items(filteredGames) { game ->
                    GameCardItem(game = game)
                }
            }
        }

        // --- Modal de Filtros ---
        if (filtersVisible) {
            FilterModalSheet(
                selectedSort = selectedSort,
                priceRange = priceRange,
                onSortSelected = { selectedSort = it },
                onPriceRangeChange = { range -> priceRange = range },
                onClose = { filtersVisible = false },
                onApply = {
                    filtersVisible = false
                }
            )
        }
    }
}

// --- LÓGICA DE FILTRADO Y BÚSQUEDA ---

private fun applyFiltersAndSearch(
    games: List<Game>,
    searchQuery: String,
    sortOption: String,
    priceRange: ClosedFloatingPointRange<Float>
): List<Game> {

    var result = games.filter { game ->

        // 1. Filtrado por Búsqueda (nombre o descripción)
        val matchesSearch = searchQuery.isBlank() ||
                game.name.contains(searchQuery, ignoreCase = true) ||
                game.desc.contains(searchQuery, ignoreCase = true)

        // 2. Filtrado por Rango de Precio
        val price = game.price.toFloat()
        val matchesPriceRange = price >= priceRange.start && price <= priceRange.endInclusive

        matchesSearch && matchesPriceRange
    }

    // 3. Ordenamiento
    result = when (sortOption) {
        "Menor Precio" -> result.sortedBy { it.price }
        "Mayor Precio" -> result.sortedByDescending { it.price }
        else -> result
    }

    return result
}


// --- Barra de Búsqueda y Botón de Filtros ---

@Composable
fun SearchAndFilterBar(
    searchQuery: String,
    onSearchChange: (String) -> Unit,
    onFilterClick: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        TextField(
            value = searchQuery,
            onValueChange = onSearchChange,
            placeholder = { Text("Buscar juegos...", color = TextMuted) },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Buscar", tint = TextMuted) },
            singleLine = true,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = SecondaryDark,
                unfocusedContainerColor = SecondaryDark,
                disabledContainerColor = SecondaryDark,
                cursorColor = PrimaryActionButton,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                focusedTextColor = TextLight,
                unfocusedTextColor = TextLight
            ),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.weight(1f)
        )

        Spacer(modifier = Modifier.width(10.dp))

        Button(
            onClick = onFilterClick,
            colors = ButtonDefaults.buttonColors(containerColor = PrimaryActionButton),
            modifier = Modifier.height(56.dp),
            shape = RoundedCornerShape(8.dp)
        ) {
            // Icono y Texto
            Icon(painterResource(id = R.drawable.explore_ico),
                contentDescription = "Filtros", tint = TextLight)
            Spacer(modifier = Modifier.width(10.dp))
            Text("Filtros", color = TextLight, fontWeight = FontWeight.SemiBold)
        }
    }
}

// --- Modal de Filtros (Solo Precio) ---

@Composable
fun FilterModalSheet(
    selectedSort: String,
    priceRange: ClosedFloatingPointRange<Float>,
    onSortSelected: (String) -> Unit,
    onPriceRangeChange: (ClosedFloatingPointRange<Float>) -> Unit,
    onClose: () -> Unit,
    onApply: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.6f))
            .clickable { onClose() },
        contentAlignment = Alignment.BottomCenter
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(enabled = false, onClick = {})
                .background(PrimaryDarkBackground, RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
                .padding(16.dp)
        ) {
            // Fila de Encabezado del Modal
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "Filtros",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = TextLight
                )
                IconButton(onClick = onClose) {
                    Icon(Icons.Default.Close, contentDescription = "Cerrar", tint = TextMuted)
                }
            }
            Spacer(modifier = Modifier.height(16.dp))

            // 1. Rango de Precio
            FilterSection(title = "Rango de Precio: $${priceRange.start.toInt()} - $${priceRange.endInclusive.toInt()}") {
                Slider(
                    value = priceRange.start,
                    onValueChange = { newValue ->
                        // Mantenemos el máximo fijo en 100f para la simulación
                        onPriceRangeChange(newValue..100f)
                    },
                    valueRange = 0f..100f,
                    steps = 99,
                    colors = SliderDefaults.colors(
                        thumbColor = PrimaryActionButton,
                        activeTrackColor = PrimaryActionButton,
                        inactiveTrackColor = SecondaryDark
                    ),
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 4.dp)
                )
            }

            // 2. Ordenar por Precio
            FilterSection(title = "Ordenar por Precio") {
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    sortOptionsPrice.forEach { option ->
                        FilterButton(
                            text = option,
                            isSelected = selectedSort == option,
                            onClick = { onSortSelected(option) }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Botón de Aplicar Filtros
            Button(
                onClick = onApply,
                colors = ButtonDefaults.buttonColors(containerColor = PrimaryActionButton),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("APLICAR FILTROS", color = TextLight, fontWeight = FontWeight.SemiBold)
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

// --- Item de Resultado de Juego (Usa solo la clase Game) ---

@Composable
fun GameCardItem(game: Game) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .background(PrimaryDarkBackground)
            .clickable { println("Ver detalle de ${game.name}") },
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Imagen (usa imageRes de tu clase Game)
        Image(
            painter = painterResource(id = game.imageRes),
            contentDescription = game.name,
            modifier = Modifier
                .size(60.dp)
                .clip(RoundedCornerShape(8.dp))
        )
        Spacer(modifier = Modifier.width(16.dp))

        // Detalles
        Column {
            Text(
                text = game.name,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                color = TextLight
            )
            // Descripción corta
            Text(
                text = game.desc,
                style = MaterialTheme.typography.bodySmall,
                color = TextMuted,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            // Precio
            Text(
                text = "$${String.format("%.2f", game.price)}",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = TextLight
            )
        }
    }
    Divider(color = SecondaryDark)
}


// --- Componentes Auxiliares ---

@Composable
fun FilterSection(title: String, content: @Composable () -> Unit) {
    Column(modifier = Modifier.padding(vertical = 12.dp)) {
        if (title.isNotEmpty()) {
            Text(
                text = title,
                fontWeight = FontWeight.SemiBold,
                color = TextLight,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }
        content()
    }
}

@Composable
fun FilterButton(text: String, isSelected: Boolean, onClick: () -> Unit) {
    val containerColor = if (isSelected) HighlightButtonActive else SecondaryDark
    val textColor = if (isSelected) TextLight else TextMuted

    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(containerColor = containerColor),
        shape = RoundedCornerShape(8.dp),
        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp),
    ) {
        Text(text, color = textColor, fontSize = 14.sp)
    }
}


// --- PREVIEW ---
@Preview(showBackground = true)
@Composable
fun PreviewExploreScreen() {

    MaterialTheme(colorScheme = darkColorScheme(
        background = PrimaryDarkBackground,
        onBackground = TextLight,
        primary = PrimaryActionButton,
        surfaceVariant = SecondaryDark
    )) {
        ExploreScreen()
    }
}