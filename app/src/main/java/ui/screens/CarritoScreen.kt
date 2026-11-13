
package com.example.appetiveexplorer.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.appetiveexplorer.data.model.Carrito
import com.example.appetiveexplorer.ui.viewmodel.CarritoViewModel
import java.text.NumberFormat
import java.util.Locale

private val AzulCarrito = Color(0xFF3E5C76)
private val FondoCarrito = Color(0xFFF7F4EF)
private val TextoCarrito = Color(0xFF263746)
private val TextoSecundarioCarrito = Color(0xFF66737D)
private val AzulClaroCarrito = Color(0xFFE7EDF2)

@Composable
fun CarritoScreen(
    cafeteriaId: Int,
    carritoViewModel: CarritoViewModel,
    onVolver: () -> Unit,
    onContinuarPedido: () -> Unit
) {
    val formatoPrecio = NumberFormat.getNumberInstance(
        Locale("es", "CO")
    )

    val productos = carritoViewModel.obtenerItems(cafeteriaId)
    val total = carritoViewModel.obtenerTotal(cafeteriaId)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(FondoCarrito)
            .padding(horizontal = 20.dp, vertical = 12.dp)
    ) {
        // Encabezado
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = 16.dp,
                    end = 16.dp,
                    top = 16.dp
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onVolver) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Volver",
                    tint = AzulCarrito
                )
            }

            Text(
                text = "Mi carrito",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 12.dp),
                color = TextoCarrito
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        if (productos.isEmpty()) {
            // Carrito vacío
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = Icons.Default.ShoppingCart,
                        contentDescription = null,
                        modifier = Modifier.size(72.dp),
                        tint = AzulClaroCarrito
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "Tu carrito está vacío",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = TextoCarrito
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Agrega productos del menú para comenzar.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = TextoSecundarioCarrito
                    )
                }
            }
        } else {
            // Lista de productos
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(
                    items = productos,
                    key = { it.producto.id }
                ) { item ->
                    ItemCarritoCard(
                        item = item,
                        formatoPrecio = formatoPrecio,
                        onAumentar = {
                            carritoViewModel.aumentarCantidad(
                                cafeteriaId,
                                item.producto.id
                            )
                        },
                        onDisminuir = {
                            carritoViewModel.disminuirCantidad(
                                cafeteriaId,
                                item.producto.id
                            )
                        },
                        onEliminar = {
                            carritoViewModel.eliminarProducto(
                                cafeteriaId,
                                item.producto.id
                            )
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Resumen del pedido
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                ),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 4.dp
                )
            ) {
                Column(
                    modifier = Modifier.padding(18.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Subtotal",
                            style = MaterialTheme.typography.bodyLarge,
                            color = TextoSecundarioCarrito
                        )

                        Text(
                            text = "$${formatoPrecio.format(total)}",
                            style = MaterialTheme.typography.bodyLarge,
                            color = TextoCarrito
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Total",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = TextoCarrito
                        )

                        Text(
                            text = "$${formatoPrecio.format(total)}",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = AzulCarrito
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = onContinuarPedido,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(54.dp),
                        shape = RoundedCornerShape(18.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = AzulCarrito
                        )
                    ) {
                        Text(
                            text = "Continuar con el pedido",
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun ItemCarritoCard(
    item: Carrito,
    formatoPrecio: NumberFormat,
    onAumentar: () -> Unit,
    onDisminuir: () -> Unit,
    onEliminar: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 3.dp
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(
                    id = item.producto.imagen
                ),
                contentDescription = item.producto.nombre,
                modifier = Modifier
                    .size(88.dp)
                    .clip(RoundedCornerShape(16.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.size(12.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = item.producto.nombre,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = TextoCarrito
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "$${formatoPrecio.format(item.producto.precio)} c/u",
                    style = MaterialTheme.typography.bodySmall,
                    color = TextoSecundarioCarrito
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        onClick = onDisminuir,
                        shape = RoundedCornerShape(10.dp),
                        color = AzulClaroCarrito
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Disminuir cantidad",
                            modifier = Modifier.padding(6.dp),
                            tint = AzulCarrito
                        )
                    }

                    Text(
                        text = item.cantidad.toString(),
                        modifier = Modifier.padding(horizontal = 12.dp),
                        fontWeight = FontWeight.Bold,
                        color = TextoCarrito
                    )

                    Surface(
                        onClick = onAumentar,
                        shape = RoundedCornerShape(10.dp),
                        color = AzulClaroCarrito
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Aumentar cantidad",
                            modifier = Modifier.padding(6.dp),
                            tint = AzulCarrito
                        )
                    }
                }
            }

            Column(
                horizontalAlignment = Alignment.End
            ) {
                IconButton(
                    onClick = onEliminar,
                    modifier = Modifier.size(36.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Eliminar producto",
                        tint = TextoSecundarioCarrito
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "$${formatoPrecio.format(item.subtotal)}",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = AzulCarrito
                )
            }
        }
    }
}
