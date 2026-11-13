
package com.example.appetiveexplorer.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.appetiveexplorer.data.model.Pedido
import com.example.appetiveexplorer.ui.viewmodel.CarritoViewModel
import java.text.NumberFormat
import java.util.Locale

private val AzulConfirmacion = Color(0xFF3E5C76)
private val FondoConfirmacion = Color(0xFFF7F4EF)
private val TextoConfirmacion = Color(0xFF263746)

@Composable
fun ConfirmarPedidoScreen(
    cafeteriaId: Int,
    carritoViewModel: CarritoViewModel,
    onVolver: () -> Unit,
    onPedidoTerminado: () -> Unit
) {
    var pedidoRegistrado by remember {
        mutableStateOf<Pedido?>(null)
    }

    var errorConfirmacion by remember {
        mutableStateOf<String?>(null)
    }

    val items = carritoViewModel.obtenerItems(cafeteriaId)

    val formatoMoneda = remember {
        NumberFormat.getCurrencyInstance(
            Locale("es", "CO")
        )
    }

    BackHandler {
        if (pedidoRegistrado != null) {
            onPedidoTerminado()
        } else {
            onVolver()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (pedidoRegistrado == null) {
            Text(
                text = "Confirmar pedido",
                style = MaterialTheme.typography.headlineSmall,
                color = TextoConfirmacion,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(16.dp))

            if (items.isEmpty()) {
                Text("Tu carrito está vacío.")

                Spacer(modifier = Modifier.height(16.dp))

                Button(onClick = onVolver) {
                    Text("Volver al carrito")
                }
            } else {
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(items) { item ->
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(
                                containerColor = Color.White
                            )
                        ) {
                            Column(
                                modifier = Modifier.padding(14.dp)
                            ) {
                                Text(
                                    text = item.producto.nombre,
                                    fontWeight = FontWeight.SemiBold
                                )

                                Text(
                                    text = "${item.cantidad} × ${
                                        formatoMoneda.format(
                                            item.producto.precio
                                        )
                                    }"
                                )

                                Text(
                                    text = "Subtotal: ${
                                        formatoMoneda.format(item.subtotal)
                                    }"
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "Total: ${
                        formatoMoneda.format(
                            items.sumOf { it.subtotal }
                        )
                    }",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )

                errorConfirmacion?.let { mensaje ->
                    Text(
                        text = mensaje,
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        val pedido =
                            carritoViewModel.registrarPedido(cafeteriaId)

                        if (pedido != null) {
                            pedidoRegistrado = pedido
                            errorConfirmacion = null
                        } else {
                            errorConfirmacion =
                                "No se pudo registrar el pedido. " +
                                        "Comprueba que el carrito tenga productos."
                        }
                    }
                ) {
                    Text("Confirmar pedido")
                }

                OutlinedButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    onClick = onVolver
                ) {
                    Text("Volver al carrito")
                }
            }
        } else {
            Text(
                text = "¡Pedido confirmado!",
                style = MaterialTheme.typography.headlineSmall,
                color = AzulConfirmacion,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text("Tu pedido se registró correctamente.")

            Spacer(modifier = Modifier.height(8.dp))

            Text("Número de pedido: #${pedidoRegistrado!!.id}")

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Total: ${
                    formatoMoneda.format(pedidoRegistrado!!.total)
                }",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Productos",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            pedidoRegistrado!!.items.forEach { item ->
                Text(
                    text = "${item.cantidad} × ${item.producto.nombre}"
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = onPedidoTerminado
            ) {
                Text("Volver al catálogo")
            }
        }
    }
}
