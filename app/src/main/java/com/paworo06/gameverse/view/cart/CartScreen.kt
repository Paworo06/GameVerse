package com.paworo06.gameverse.ui.cart

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
// Importaciones de tus modelos de datos
import com.paworo06.gameverse.data.model.CartItem
import com.paworo06.gameverse.data.model.Game
import java.math.BigDecimal
import java.math.RoundingMode

// --- DEFINICIÓN DE COLORES Y CONSTANTES ---
// Colores definidos basados en las especificaciones RGB proporcionadas
val PrimaryDarkBackground = Color(0xFF191121) // Fondo principal oscuro (RGB 25, 17, 33)
val PrimaryActionButton = Color(0xFF8C30E8)   // Color de acento para el botón de pago (RGB 140, 48, 232)
val TextLight = Color.White                   // Texto principal claro
val TextMuted = Color(0xFFCCCCCC)             // Texto secundario, sutil
val ControlButtonBackground = Color(0xFF333333) // Fondo gris oscuro para los controles de cantidad

@Composable
fun CartScreenStyledMinimal() {

    // 1. DATOS DE EJEMPLO (Simulación del estado del carrito)
    // Se utiliza 'remember' para que estos datos de ejemplo persistan durante las recomposiciones.
    val sampleItems = remember {
        listOf(
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

    // Cálculo del subtotal usando la función auxiliar
    val subtotal = calculateTotal(sampleItems)

    // ESTRUCTURA PRINCIPAL DE LA PANTALLA
    // Se utiliza Column para apilar el encabezado, la lista de ítems y el resumen de pago.
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(PrimaryDarkBackground) // Aplicación del color de fondo oscuro
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
                .align(Alignment.CenterHorizontally) // Centra el texto
        )

        // --- CONTENIDO: LISTA DE ÍTEMS ---
        // Column con verticalScroll: Actúa como la lista desplazable.
        Column(
            modifier = Modifier
                .weight(1f) // Ocupa todo el espacio vertical disponible
                .verticalScroll(rememberScrollState())
        ) {
            // Itera sobre la lista de productos en el carrito
            sampleItems.forEach { cartItem ->
                // Renderiza la fila de cada producto con los controles de cantidad
                CartItemRowStyledV3(
                    cartItem = cartItem,
                    // Funciones de callback para manejar eventos (simulando la interacción con el ViewModel)
                    onQuantityChange = { newQ -> println("Lógica: Cambiar ${cartItem.game.name} a $newQ") },
                    onRemove = { println("Lógica: Eliminar ítem ${cartItem.game.id}") }
                )
                // Separador sutil entre ítems
                Divider(color = TextMuted.copy(alpha = 0.3f))
            }
        }

        // --- RESUMEN DEL PAGO (Footer Fijo) ---
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
                .background(PrimaryDarkBackground) // Mantiene el fondo consistente
                .padding(vertical = 8.dp)
        ) {
            // Fila de Total (Se usa 'Total' para simplificar, aunque el cálculo es del subtotal)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Etiqueta "Total"
                Text(
                    text = "Total:",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = TextLight
                )
                // Valor del Total
                Text(
                    text = "$${subtotal.toPlainString()}",
                    style = MaterialTheme.typography.titleLarge,
                    color = TextLight,
                    fontWeight = FontWeight.Bold
                )
            }

            // Separador antes del botón de acción
            Divider(color = TextMuted.copy(alpha = 0.5f), modifier = Modifier.padding(vertical = 10.dp))
            Spacer(modifier = Modifier.height(10.dp))

            // Botón de FINALIZAR COMPRA
            Button(
                onClick = { println("Proceder al Pago Total: $subtotal") },
                colors = ButtonDefaults.buttonColors(containerColor = PrimaryActionButton), // Aplicación del color púrpura fuerte
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(10.dp), // Esquinas redondeadas
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
    onQuantityChange: (Int) -> Unit, // Callback para cambiar cantidad
    onRemove: () -> Unit             // Callback para eliminar ítem
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
            // Nombre del juego
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
                        onQuantityChange(quantity - 1) // Disminuir si es mayor a 1
                    } else {
                        onRemove() // Llamar a la función de remover si llega a 1
                    }
                },
                modifier = Modifier.size(40.dp)
            ) {
                Text("-", color = TextLight, fontSize = 25.sp, fontWeight = FontWeight.SemiBold)
            }

            // Cantidad como Número
            Text(
                text = quantity.toString(),
                color = TextLight,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 12.dp) // Espacio para separar de los botones
            )

            // Botón de Aumentar (+)
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
    // Calcula el subtotal sumando (precio del juego * cantidad) para cada ítem.
    // Utiliza BigDecimal para manejar la precisión de la moneda.
    return items.sumOf { item ->
        item.game.price.toBigDecimal().multiply(item.quanty.toBigDecimal())
    }.setScale(2, RoundingMode.HALF_UP) // Redondea a 2 decimales
}

// --- PREVIEW ---
@Preview(showBackground = true)
@Composable
fun PreviewCartScreenStyledMinimal() {
    // Definiciones placeholder internas para que el Preview pueda compilar las clases externas
    data class Game(val id: Int, val name: String, val desc: String, val price: Double, val imageRes: Int)
    data class CartItem (var game: Game, var quanty: Int)

    // Aplica un tema oscuro con los colores personalizados para simular el entorno real
    MaterialTheme(colorScheme = darkColorScheme(
        background = PrimaryDarkBackground,
        onBackground = TextLight,
        primary = PrimaryActionButton,
        surfaceVariant = PrimaryDarkBackground
    )) {
        CartScreenStyledMinimal()
    }
}