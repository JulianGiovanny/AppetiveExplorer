
package com.example.appetiveexplorer.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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

    var metodoPago by remember {
        mutableStateOf("Efectivo")
    }

    var metodoConsumo by remember {
        mutableStateOf("En cafetería")
    }

    val items = carritoViewModel.obtenerItems(cafeteriaId)

    val formatoMoneda = remember {
        NumberFormat.getCurrencyInstance(Locale("es", "CO"))
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
            .background(FondoConfirmacion)
            .padding(
                start = 20.dp,
                end = 20.dp,
                top = 36.dp,
                bottom = 20.dp
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // PANTALLA DE CONFIRMACIÓN DEL PEDIDO
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

                    // PRODUCTOS DEL PEDIDO
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
                                    fontWeight = FontWeight.SemiBold,
                                    color = TextoConfirmacion
                                )

                                Text(
                                    text = "${item.cantidad} × ${
                                        formatoMoneda.format(item.producto.precio)
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

                    // MÉTODO DE CONSUMO
                    item {

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "Método de consumo",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = TextoConfirmacion
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {

                            OutlinedButton(
                                modifier = Modifier.weight(1f),
                                onClick = {
                                    metodoConsumo = "En cafetería"
                                },
                                colors = ButtonDefaults.outlinedButtonColors(
                                    containerColor = if (
                                        metodoConsumo == "En cafetería"
                                    ) {
                                        AzulConfirmacion
                                    } else {
                                        Color.Transparent
                                    },
                                    contentColor = if (
                                        metodoConsumo == "En cafetería"
                                    ) {
                                        Color.White
                                    } else {
                                        AzulConfirmacion
                                    }
                                )
                            ) {
                                Text("En cafetería")
                            }

                            OutlinedButton(
                                modifier = Modifier.weight(1f),
                                onClick = {
                                    metodoConsumo = "Para llevar"
                                },
                                colors = ButtonDefaults.outlinedButtonColors(
                                    containerColor = if (
                                        metodoConsumo == "Para llevar"
                                    ) {
                                        AzulConfirmacion
                                    } else {
                                        Color.Transparent
                                    },
                                    contentColor = if (
                                        metodoConsumo == "Para llevar"
                                    ) {
                                        Color.White
                                    } else {
                                        AzulConfirmacion
                                    }
                                )
                            ) {
                                Text("Para llevar")
                            }
                        }
                    }

                    // MÉTODO DE PAGO
                    item {

                        Spacer(modifier = Modifier.height(12.dp))

                        Text(
                            text = "Método de pago",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = TextoConfirmacion
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {

                            OutlinedButton(
                                modifier = Modifier.weight(1f),
                                onClick = {
                                    metodoPago = "Efectivo"
                                },
                                colors = ButtonDefaults.outlinedButtonColors(
                                    containerColor = if (
                                        metodoPago == "Efectivo"
                                    ) {
                                        AzulConfirmacion
                                    } else {
                                        Color.Transparent
                                    },
                                    contentColor = if (
                                        metodoPago == "Efectivo"
                                    ) {
                                        Color.White
                                    } else {
                                        AzulConfirmacion
                                    }
                                )
                            ) {
                                Text("Efectivo")
                            }

                            OutlinedButton(
                                modifier = Modifier.weight(1f),
                                onClick = {
                                    metodoPago = "Tarjeta"
                                },
                                colors = ButtonDefaults.outlinedButtonColors(
                                    containerColor = if (
                                        metodoPago == "Tarjeta"
                                    ) {
                                        AzulConfirmacion
                                    } else {
                                        Color.Transparent
                                    },
                                    contentColor = if (
                                        metodoPago == "Tarjeta"
                                    ) {
                                        Color.White
                                    } else {
                                        AzulConfirmacion
                                    }
                                )
                            ) {
                                Text("Tarjeta")
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // TOTAL
                Text(
                    text = "Total: ${
                        formatoMoneda.format(items.sumOf { it.subtotal })
                    }",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = TextoConfirmacion
                )

                // MENSAJE DE ERROR
                errorConfirmacion?.let { mensaje ->
                    Text(
                        text = mensaje,
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                // CONFIRMAR PEDIDO
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {

                        val pedido = carritoViewModel.registrarPedido(
                            cafeteriaId = cafeteriaId,
                            metodoPago = metodoPago,
                            metodoConsumo = metodoConsumo
                        )

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

            // PANTALLA DE PEDIDO CONFIRMADO
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
                text = "Método de consumo: ${
                    pedidoRegistrado!!.metodoConsumo
                }"
            )

            Text(
                text = "Método de pago: ${
                    pedidoRegistrado!!.metodoPago
                }"
            )

            Spacer(modifier = Modifier.height(12.dp))

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
