package com.paworo06.gameverse.view.cart

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.* // Importación clave para remember, mutableStateOf, getValue, setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.paworo06.gameverse.data.model.CartItem
import com.paworo06.gameverse.data.model.Game
import java.math.BigDecimal
import java.math.RoundingMode

// --- DEFINICIÓN DE COLORES Y CONSTANTES ---
val PrimaryDarkBackground = Color(0xFF191121)
val PrimaryActionButton = Color(0xFF8C30E8)
val TextLight = Color.White
val TextMuted = Color(0xFFCCCCCC)
val ControlButtonBackground = Color(0xFF333333)

// Datos de ejemplo iniciales (fuera de la composición)
private fun initialSampleItems(): List<CartItem> {
    return listOf(
        CartItem(
            game = Game(id = 1, name = "Elden Ring", desc = "...", price = 69.99, imageRes = 0),
            quanty = 1
        ),
        CartItem(
            game = Game(id = 2, name = "Hollow Knight", desc = "...", price = 14.99, imageRes = 2),
            quanty = 2
        ),
        CartItem(
            game = Game(id = 3, name = "God of War Ragnarök", desc = "...", price = 55.00, imageRes = 3),
            quanty = 1
        ),
    )
}

@Composable
fun CartScreen() {

    // 1. ESTADO DEL CARRITO: Usamos mutableStateOf para que la lista sea observable.
    // 'by' permite usar cartItems = ... para actualizar el estado.
    var cartItems by remember { mutableStateOf(initialSampleItems()) }

    // 2. LÓGICA CENTRAL: Función para actualizar la cantidad de un ítem.
    fun updateQuantity(itemId: Int, newQuantity: Int) {
        // Mapeamos la lista actual para encontrar el ítem a modificar
        cartItems = cartItems.map { item ->
            if (item.game.id == itemId) {
                // Devolvemos una COPIA del CartItem con la nueva cantidad.
                // Esto es crucial para la inmutabilidad y la recomposición.
                item.copy(quanty = newQuantity)
            } else {
                // Devolvemos el ítem sin modificar
                item
            }
        }
    }

    // 3. LÓGICA CENTRAL: Función para eliminar un ítem.
    fun removeItem(itemId: Int) {
        // Filtramos la lista, excluyendo el ítem a eliminar. Esto crea una nueva lista.
        cartItems = cartItems.filter { it.game.id != itemId }
    }

    // 4. Cálculo del subtotal: Se recalculá automáticamente cada vez que 'cartItems' cambia.
    val subtotal = calculateTotal(cartItems)

    // ESTRUCTURA PRINCIPAL DE LA PANTALLA
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(PrimaryDarkBackground)
            .padding(horizontal = 16.dp)
    ) {

        // --- ENCABEZADO / TÍTULO DE LA PANTALLA ---
        Text(
            text = "CARRITO DE JUEGOS",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
            color = TextLight,
            modifier = Modifier
                .padding(top = 16.dp, bottom = 16.dp)
                .align(Alignment.CenterHorizontally)
        )

        // --- CONTENIDO: LISTA DE ÍTEMS ---
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
        ) {
            // Itera sobre la lista de estado (cartItems)
            cartItems.forEach { cartItem ->
                // Renderiza la fila, pasando las funciones de actualización con el ID del juego.
                CartItemRowStyledV3(
                    cartItem = cartItem,
                    onQuantityChange = { newQ -> updateQuantity(cartItem.game.id, newQ) },
                    onRemove = { removeItem(cartItem.game.id) }
                )
                Divider(color = TextMuted.copy(alpha = 0.3f))
            }
        }

        // --- RESUMEN DEL PAGO (Footer Fijo) ---
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
                .background(PrimaryDarkBackground)
                .padding(vertical = 8.dp)
        ) {
            // Fila de Total (Se actualiza automáticamente)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Total:",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = TextLight
                )
                Text(
                    text = "$${subtotal.toPlainString()}",
                    style = MaterialTheme.typography.titleLarge,
                    color = TextLight,
                    fontWeight = FontWeight.Bold
                )
            }

            Divider(color = TextMuted.copy(alpha = 0.5f), modifier = Modifier.padding(vertical = 10.dp))
            Spacer(modifier = Modifier.height(10.dp))

            // Botón de FINALIZAR COMPRA
            Button(
                onClick = { println("Proceder al Pago Total: $subtotal") },
                colors = ButtonDefaults.buttonColors(containerColor = PrimaryActionButton),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(10.dp),
                contentPadding = PaddingValues(12.dp)
            ) {
                Text("FINALIZAR COMPRA", color = TextLight, fontWeight = FontWeight.SemiBold)
            }
        }
    }
}

// --- COMPONENTE: FILA DE ÍTEM DEL CARRITO (CartItemRowStyledV3) ---

@Composable
fun CartItemRowStyledV3(
    cartItem: CartItem,
    // La fila recibe la nueva cantidad a establecer (newQ)
    onQuantityChange: (newQ: Int) -> Unit,
    onRemove: () -> Unit
) {
    val game = cartItem.game
    // La cantidad ahora se lee directamente del objeto cartItem (que viene del estado global)
    val quantity = cartItem.quanty

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = game.name,
                style = MaterialTheme.typography.titleMedium,
                color = TextLight,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = "$${game.price}",
                style = MaterialTheme.typography.titleLarge,
                color = TextLight,
                fontWeight = FontWeight.ExtraBold,
                modifier = Modifier.padding(top = 4.dp)
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        // Controles de Cantidad (Aumentar y Disminuir)
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .background(ControlButtonBackground, shape = RoundedCornerShape(20.dp))
                .height(40.dp)
        ) {

            // Botón de Disminuir (-)
            IconButton(
                onClick = {
                    if (quantity > 1) {
                        onQuantityChange(quantity - 1) // Llama al padre para reducir y actualizar la lista
                    } else {
                        onRemove() // Llama al padre para eliminar el ítem
                    }
                },
                modifier = Modifier.size(40.dp)
            ) {
                Text("-", color = TextLight, fontSize = 25.sp, fontWeight = FontWeight.SemiBold)
            }

            // Cantidad como Número (Muestra el valor actualizado del estado global)
            Text(
                text = quantity.toString(),
                color = TextLight,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 12.dp)
            )

            // Botón de Aumentar (+)
            IconButton(
                onClick = { onQuantityChange(quantity + 1) }, // Llama al padre para incrementar y actualizar la lista
                modifier = Modifier.size(40.dp)
            ) {
                Text("+", color = TextLight, fontSize = 22.sp, fontWeight = FontWeight.SemiBold)
            }
        }
    }
}


// --- FUNCIÓN AUXILIAR DE LÓGICA ---
private fun calculateTotal(items: List<CartItem>): BigDecimal {
    return items.sumOf { item ->
        item.game.price.toBigDecimal().multiply(item.quanty.toBigDecimal())
    }.setScale(2, RoundingMode.HALF_UP)
}

// --- PREVIEW ---
@Preview(showBackground = true)
@Composable
fun PreviewCartScreen() {
    // Definiciones placeholder internas para que el Preview pueda compilar las clases externas
    data class Game(val id: Int, val name: String, val desc: String, val price: Double, val imageRes: Int)
    data class CartItem (var game: Game, var quanty: Int)

    MaterialTheme(colorScheme = darkColorScheme(
        background = PrimaryDarkBackground,
        onBackground = TextLight,
        primary = PrimaryActionButton,
        surfaceVariant = PrimaryDarkBackground
    )) {
        CartScreen()
    }
}