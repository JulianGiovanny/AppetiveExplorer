package com.example.appetiveexplorer.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.appetiveexplorer.data.model.Producto
import com.example.appetiveexplorer.data.repository.CafeteriaRepository
import com.example.appetiveexplorer.data.repository.ProductoRepository
import com.example.appetiveexplorer.ui.components.ProductCard
import com.example.appetiveexplorer.ui.components.SearchBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import com.example.appetiveexplorer.ui.viewmodel.CarritoViewModel
import android.widget.Toast
import androidx.compose.ui.platform.LocalContext
import com.example.appetiveexplorer.ui.screens.ConfirmarPedidoScreen



private val AzulAppetive = Color(0xFF3E5C76)
private val FondoAppetive = Color(0xFFF7F4EF)
private val TextoPrincipal = Color(0xFF263746)
private val TextoSecundario = Color(0xFF66737D)
private val AzulClaro = Color(0xFFE7EDF2)


// HOME SCREEN

@Composable
fun HomeScreen(
    cafeteriaId: Int,
    onCambiarCafeteria: () -> Unit,
    carritoViewModel: CarritoViewModel
) {

    val context = LocalContext.current

    fun mostrarMensajeProductoAgregado() {
        Toast.makeText(
            context,
            "¡Producto agregado al carrito!",
            Toast.LENGTH_SHORT
        ).show()
    }

    // DATOS DE LA CAFETERÍA
    val cafeteriaRepository = CafeteriaRepository()
    val cafeterias = cafeteriaRepository.obtenerCafeterias()

    val cafeteria = cafeterias.find {
        it.id == cafeteriaId
    }


    // ESTADOS
    var textoBusqueda by remember {
        mutableStateOf("")
    }

    var categoriaSeleccionada by remember {
        mutableStateOf("Todos")
    }

    var productoSeleccionado by remember {
        mutableStateOf<Producto?>(null)
    }

    var mostrarHorarios by remember {
        mutableStateOf(false)
    }

    var mostrarCarrito by remember {
        mutableStateOf(false)
    }

    var mostrarConfirmacionPedido by remember {
        mutableStateOf(false)
    }

    val cantidadCarrito =
        carritoViewModel.obtenerCantidadTotal(cafeteriaId)

    val gridState = rememberLazyGridState()

    // DETECTAR SI EL USUARIO HA DESPLAZADO LA PANTALLA
    val mostrandoBarraCompacta =
        gridState.firstVisibleItemIndex > 0 ||
                gridState.firstVisibleItemScrollOffset > 80


    // PANTALLA DEL CARRITO
    if (mostrarConfirmacionPedido) {
        ConfirmarPedidoScreen(
            cafeteriaId = cafeteriaId,
            carritoViewModel = carritoViewModel,
            onVolver = {
                mostrarConfirmacionPedido = false
                mostrarCarrito = true
            },
            onPedidoTerminado = {
                mostrarConfirmacionPedido = false
                mostrarCarrito = false
            }
        )
        return
    }

    if (mostrarCarrito) {
        BackHandler {
            mostrarCarrito = false
        }

        CarritoScreen(
            cafeteriaId = cafeteriaId,
            carritoViewModel = carritoViewModel,
            onVolver = {
                mostrarCarrito = false
            },
            onContinuarPedido = {
                mostrarCarrito = false
                mostrarConfirmacionPedido = true
            }
        )

        return
    }

    // PRODUCTO SELECCIONADO

    if (productoSeleccionado != null) {

        BackHandler {
            productoSeleccionado = null
        }

        ProductDetailScreen(
            producto = productoSeleccionado!!,

            onVolver = {
                productoSeleccionado = null
            },

            onAgregarCarrito = {
                val producto = productoSeleccionado

                if (producto != null) {
                    carritoViewModel.agregarProducto(
                        cafeteriaId,
                        producto
                    )

                    mostrarMensajeProductoAgregado()
                }
            }
        )

        return
    }


    // FILTRAR PRODUCTOS
    val productos = obtenerProductosFiltrados(
        textoBusqueda = textoBusqueda,
        categoriaSeleccionada = categoriaSeleccionada
    )

    // ESTRUCTURA PRINCIPAL
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(FondoAppetive)
            .safeDrawingPadding()
    ) {

        // BARRA COMPACTA
        // Aparece cuando el usuario baja.
        AnimatedVisibility(
            visible = mostrandoBarraCompacta,

            enter = fadeIn() +
                    slideInVertically(
                        initialOffsetY = { -it / 2 }
                    ),

            exit = fadeOut() +
                    slideOutVertically(
                        targetOffsetY = { -it / 2 }
                    )
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(AzulClaro)
                    .padding(
                        horizontal = 20.dp,
                        vertical = 10.dp
                    ),

                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = "←",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = AzulAppetive,

                    modifier = Modifier.clickable {
                        onCambiarCafeteria()
                    }
                )

                Spacer(
                    modifier = Modifier.size(10.dp)
                )

                Text(
                    text = cafeteria?.nombre ?: "Cafetería",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = TextoPrincipal
                )
            }
        }

        // CONTENIDO DESPLAZABLE
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),

            state = gridState,

            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),

            horizontalArrangement = Arrangement.spacedBy(12.dp),

            verticalArrangement = Arrangement.spacedBy(16.dp),

            contentPadding = androidx.compose.foundation.layout.PaddingValues(
                start = 20.dp,
                end = 20.dp,
                top = 16.dp,
                bottom = 20.dp
            )
        ) {

            // INFORMACIÓN DE LA CAFETERÍA
            item(
                span = {
                    GridItemSpan(maxLineSpan)
                }
            ) {

                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {


                    // CAMBIAR CAFETERÍA
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                onCambiarCafeteria()
                            },

                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Text(
                            text = "←",
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold,
                            color = AzulAppetive
                        )

                        Spacer(
                            modifier = Modifier.size(8.dp)
                        )

                        Text(
                            text = "Cambiar cafetería",
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.SemiBold,
                            color = AzulAppetive
                        )
                    }

                    Spacer(
                        modifier = Modifier.height(14.dp)
                    )


                    // TARJETA DE CAFETERÍA
                    if (cafeteria != null) {

                        Card(
                            modifier = Modifier.fillMaxWidth(),

                            shape = RoundedCornerShape(24.dp),

                            colors = CardDefaults.cardColors(
                                containerColor = Color.White
                            ),

                            elevation = CardDefaults.cardElevation(
                                defaultElevation = 5.dp
                            )
                        ) {

                            Column {

                                // INFORMACIÓN PRINCIPAL
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(14.dp),

                                    verticalAlignment = Alignment.CenterVertically
                                ) {

                                    Image(
                                        painter = painterResource(
                                            id = cafeteria.imagen
                                        ),

                                        contentDescription =
                                            cafeteria.nombre,

                                        modifier = Modifier
                                            .size(105.dp)
                                            .clip(
                                                RoundedCornerShape(20.dp)
                                            ),

                                        contentScale =
                                            ContentScale.Crop
                                    )

                                    Spacer(
                                        modifier = Modifier.size(14.dp)
                                    )

                                    Column(
                                        modifier = Modifier.weight(1f)
                                    ) {

                                        Text(
                                            text = cafeteria.nombre,

                                            style = MaterialTheme
                                                .typography
                                                .titleLarge,

                                            fontWeight = FontWeight.Bold,

                                            color = AzulAppetive
                                        )

                                        Spacer(
                                            modifier = Modifier.height(5.dp)
                                        )

                                        Text(
                                            text = "📍 ${cafeteria.direccion}",

                                            style = MaterialTheme
                                                .typography
                                                .bodyMedium,

                                            color = TextoSecundario
                                        )

                                        Spacer(
                                            modifier = Modifier.height(9.dp)
                                        )


                                        // BOTÓN HORARIOS
                                        Surface(
                                            onClick = {
                                                mostrarHorarios =
                                                    !mostrarHorarios
                                            },

                                            shape =
                                                RoundedCornerShape(12.dp),

                                            color = AzulClaro
                                        ) {

                                            Text(
                                                text =
                                                    if (mostrarHorarios) {
                                                        "Ocultar horarios"
                                                    } else {
                                                        "🕐 Ver horarios"
                                                    },

                                                modifier = Modifier.padding(
                                                    horizontal = 12.dp,
                                                    vertical = 7.dp
                                                ),

                                                style =
                                                    MaterialTheme
                                                        .typography
                                                        .labelLarge,

                                                fontWeight =
                                                    FontWeight.SemiBold,

                                                color =
                                                    TextoPrincipal
                                            )
                                        }
                                    }
                                }

                                // HORARIOS
                                AnimatedVisibility(
                                    visible = mostrarHorarios,

                                    enter =
                                        fadeIn() +
                                                slideInVertically(),

                                    exit =
                                        fadeOut() +
                                                slideOutVertically()
                                ) {

                                    Column(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .background(
                                                AzulClaro
                                            )
                                            .padding(
                                                horizontal = 18.dp,
                                                vertical = 14.dp
                                            )
                                    ) {

                                        Text(
                                            text =
                                                "Horarios de atención",

                                            style =
                                                MaterialTheme
                                                    .typography
                                                    .titleSmall,

                                            fontWeight =
                                                FontWeight.Bold,

                                            color =
                                                TextoPrincipal
                                        )

                                        Spacer(
                                            modifier = Modifier.height(8.dp)
                                        )

                                        cafeteria.horarios.forEach {
                                                horario ->

                                            Row(
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .padding(
                                                        vertical = 3.dp
                                                    ),

                                                horizontalArrangement =
                                                    Arrangement
                                                        .SpaceBetween
                                            ) {

                                                Text(
                                                    text =
                                                        horario.dias,

                                                    style =
                                                        MaterialTheme
                                                            .typography
                                                            .bodyMedium,

                                                    fontWeight =
                                                        FontWeight.SemiBold,

                                                    color =
                                                        TextoPrincipal
                                                )

                                                Text(
                                                    text =
                                                        "${horario.horaApertura} - " +
                                                                horario.horaCierre,

                                                    style =
                                                        MaterialTheme
                                                            .typography
                                                            .bodyMedium,

                                                    color =
                                                        TextoSecundario
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }

                    Spacer(
                        modifier = Modifier.height(4.dp)
                    )
                }
            }



            // ACCESO AL CARRITO
            item(
                span = {
                    GridItemSpan(maxLineSpan)
                }
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = {
                            mostrarCarrito = true
                        }
                    ) {
                        BadgedBox(
                            badge = {
                                if (cantidadCarrito > 0) {
                                    Badge {
                                        Text(
                                            text = cantidadCarrito.toString()
                                        )
                                    }
                                }
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.ShoppingCart,
                                contentDescription = "Abrir carrito",
                                tint = AzulAppetive
                            )
                        }
                    }
                }
            }



            // BUSCADOR
            item(
                span = {
                    GridItemSpan(maxLineSpan)
                }
            ) {

                SearchBar(
                    texto = textoBusqueda,

                    onTextoChange = {
                        textoBusqueda = it
                    }
                )
            }

            // CATEGORÍAS
            item(
                span = {
                    GridItemSpan(maxLineSpan)
                }
            ) {

                Row(
                    modifier = Modifier.fillMaxWidth(),

                    horizontalArrangement =
                        Arrangement.spacedBy(8.dp)
                ) {

                    CategoriaChip(
                        texto = "Todos",

                        seleccionada =
                            categoriaSeleccionada == "Todos",

                        onClick = {
                            categoriaSeleccionada = "Todos"
                        }
                    )

                    CategoriaChip(
                        texto = "Bebidas",

                        seleccionada =
                            categoriaSeleccionada == "Bebidas",

                        onClick = {
                            categoriaSeleccionada = "Bebidas"
                        }
                    )

                    CategoriaChip(
                        texto = "Harinas",

                        seleccionada =
                            categoriaSeleccionada == "Harinas",

                        onClick = {
                            categoriaSeleccionada = "Harinas"
                        }
                    )

                    CategoriaChip(
                        texto = "Dulces",

                        seleccionada =
                            categoriaSeleccionada == "Dulces",

                        onClick = {
                            categoriaSeleccionada = "Dulces"
                        }
                    )
                }
            }

            // PRODUCTOS
            items(productos) { producto ->

                ProductCard(
                    producto = producto,
                    onClick = {
                        productoSeleccionado = producto
                    },
                    onAddClick = {
                        carritoViewModel.agregarProducto(
                            cafeteriaId,
                            producto
                        )

                        mostrarMensajeProductoAgregado()
                    }
                )
            }
        }
    }
}

// CHIP DE CATEGORÍA
@Composable
private fun CategoriaChip(
    texto: String,
    seleccionada: Boolean,
    onClick: () -> Unit
) {

    Surface(
        onClick = onClick,

        shape = RoundedCornerShape(16.dp),

        color =
            if (seleccionada) {
                AzulAppetive
            } else {
                AzulClaro
            }
    ) {

        Text(
            text = texto,

            modifier = Modifier.padding(
                horizontal = 14.dp,
                vertical = 9.dp
            ),

            style =
                MaterialTheme.typography.labelLarge,

            fontWeight =
                FontWeight.SemiBold,

            color =
                if (seleccionada) {
                    Color.White
                } else {
                    TextoPrincipal
                }
        )
    }
}


// FILTRAR PRODUCTOS
private fun obtenerProductosFiltrados(
    textoBusqueda: String,
    categoriaSeleccionada: String
): List<Producto> {

    val repository = ProductoRepository()

    val productos =
        repository.obtenerProductos()

    return productos.filter { producto ->

        val coincideBusqueda =
            producto.nombre.contains(
                textoBusqueda,
                ignoreCase = true
            )

        val coincideCategoria =
            categoriaSeleccionada == "Todos" ||
                    producto.categoria ==
                    categoriaSeleccionada

        coincideBusqueda &&
                coincideCategoria
    }
}
