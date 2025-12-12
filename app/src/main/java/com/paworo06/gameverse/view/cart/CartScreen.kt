package com.paworo06.gameverse.view.cart

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.paworo06.gameverse.data.model.CartItem
import com.paworo06.gameverse.view.explore.cartManager
import java.math.BigDecimal
import java.math.RoundingMode

// --- DEFINICIÓN DE COLORES Y CONSTANTES ---
val PrimaryDarkBackground = Color(0xFF191121)
val PrimaryActionButton = Color(0xFF8C30E8)
val TextLight = Color.White
val TextMuted = Color(0xFFCCCCCC)
val ControlButtonBackground = Color(0xFF333333)

@Composable
fun CartScreen() {
    // 1. ESTADO DEL CARRITO: Inicializamos el estado obteniendo los ítems del CartManager global.
    // Usamos toList() para crear una copia y asegurar que Compose detecte cambios si la lista muta.
    var cartItems by remember { mutableStateOf(cartManager.getCartItems().toList()) }

    // 2. LÓGICA CENTRAL: Función para actualizar la cantidad de un ítem.
    fun updateQuantity(itemId: Int, newQuantity: Int) {
        // Actualizamos el manager global
        cartManager.updateQuantity(itemId, newQuantity)
        // Actualizamos el estado local para recomponer la UI
        cartItems = cartManager.getCartItems().toList()
    }

    // 3. LÓGICA CENTRAL: Función para eliminar un ítem.
    fun removeItem(itemId: Int) {
        // Eliminamos del manager global
        cartManager.removeGame(itemId)
        // Actualizamos el estado local
        cartItems = cartManager.getCartItems().toList()
    }

    // 4. Cálculo del subtotal
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
        if (cartItems.isEmpty()) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "El carrito está vacío",
                    style = MaterialTheme.typography.titleMedium,
                    color = TextMuted
                )
            }
        } else {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
            ) {
                cartItems.forEach { cartItem ->
                    CartItemRowStyledV3(
                        cartItem = cartItem,
                        onQuantityChange = { newQ -> updateQuantity(cartItem.game.id, newQ) },
                        onRemove = { removeItem(cartItem.game.id) }
                    )
                    HorizontalDivider(color = TextMuted.copy(alpha = 0.3f))
                }
            }
        }

        // --- RESUMEN DEL PAGO (Footer Fijo) ---
        // Solo mostramos el footer si hay items
        if (cartItems.isNotEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
                    .background(PrimaryDarkBackground)
                    .padding(vertical = 8.dp)
            ) {
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
                        text = "€${subtotal.toPlainString()}",
                        style = MaterialTheme.typography.titleLarge,
                        color = TextLight,
                        fontWeight = FontWeight.Bold
                    )
                }

                HorizontalDivider(color = TextMuted.copy(alpha = 0.5f), modifier = Modifier.padding(vertical = 10.dp))
                Spacer(modifier = Modifier.height(10.dp))

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
}

// --- COMPONENTE: FILA DE ÍTEM DEL CARRITO ---

@Composable
fun CartItemRowStyledV3(
    cartItem: CartItem,
    onQuantityChange: (newQ: Int) -> Unit,
    onRemove: () -> Unit
) {
    val game = cartItem.game
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
                text = "€${game.price}",
                style = MaterialTheme.typography.titleLarge,
                color = TextLight,
                fontWeight = FontWeight.ExtraBold,
                modifier = Modifier.padding(top = 4.dp)
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .background(ControlButtonBackground, shape = RoundedCornerShape(20.dp))
                .height(40.dp)
        ) {
            IconButton(
                onClick = {
                    if (quantity > 1) {
                        onQuantityChange(quantity - 1)
                    } else {
                        onRemove()
                    }
                },
                modifier = Modifier.size(40.dp)
            ) {
                Text("-", color = TextLight, fontSize = 25.sp, fontWeight = FontWeight.SemiBold)
            }

            Text(
                text = quantity.toString(),
                color = TextLight,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 12.dp)
            )

            IconButton(
                onClick = { onQuantityChange(quantity + 1) },
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
    MaterialTheme(colorScheme = darkColorScheme(
        background = PrimaryDarkBackground,
        onBackground = TextLight,
        primary = PrimaryActionButton,
        surfaceVariant = PrimaryDarkBackground
    )) {
        CartScreen()
    }
}
