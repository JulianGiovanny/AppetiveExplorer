package com.example.appetiveexplorer.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ShoppingCart
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
import com.example.appetiveexplorer.data.model.Producto
import java.text.NumberFormat
import java.util.Locale
import androidx.compose.foundation.layout.safeDrawingPadding


// COLORES APPETIVE
private val AzulAppetive = Color(0xFF3E5C76)
private val FondoAppetive = Color(0xFFF7F4EF)
private val TextoPrincipal = Color(0xFF263746)
private val TextoSecundario = Color(0xFF66737D)
private val AzulClaro = Color(0xFFE7EDF2)


// DETALLE DEL PRODUCTO
@Composable
fun ProductDetailScreen(
    producto: Producto,
    onVolver: () -> Unit,
    onAgregarCarrito: () -> Unit
) {

    val formatoPrecio =
        NumberFormat.getNumberInstance(
            Locale("es", "CO")
        )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(FondoAppetive)
            .safeDrawingPadding()
            .padding(
                start = 20.dp,
                end = 20.dp,
                top = 12.dp,
                bottom = 20.dp
            )
    ) {

        // ENCABEZADO
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),

            verticalAlignment = Alignment.CenterVertically
        ) {

            IconButton(
                onClick = onVolver,
                modifier = Modifier.size(44.dp)
            ) {

                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Volver",
                    tint = AzulAppetive
                )
            }

            Spacer(
                modifier = Modifier.size(6.dp)
            )

            Text(
                text = "Detalle del producto",

                style =
                    MaterialTheme.typography.titleLarge,

                fontWeight =
                    FontWeight.Bold,

                color = TextoPrincipal
            )
        }

        Spacer(
            modifier = Modifier.height(16.dp)
        )


        // CONTENIDO
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),

            verticalArrangement =
                Arrangement.Top
        ) {


            // IMAGEN DEL PRODUCTO

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(270.dp),

                shape =
                    RoundedCornerShape(24.dp),

                colors =
                    CardDefaults.cardColors(
                        containerColor = Color.White
                    ),

                elevation =
                    CardDefaults.cardElevation(
                        defaultElevation = 5.dp
                    )
            ) {

                Image(
                    painter =
                        painterResource(
                            id = producto.imagen
                        ),

                    contentDescription =
                        producto.nombre,

                    modifier = Modifier
                        .fillMaxSize()
                        .clip(
                            RoundedCornerShape(24.dp)
                        ),

                    contentScale =
                        ContentScale.Crop
                )
            }

            Spacer(
                modifier = Modifier.height(28.dp)
            )


            // NOMBRE

            Text(
                text = producto.nombre,

                style =
                    MaterialTheme.typography.headlineSmall,

                fontWeight =
                    FontWeight.Bold,

                color =
                    TextoPrincipal
            )

            Spacer(
                modifier = Modifier.height(6.dp)
            )


            // CATEGORÍA
            Surface(
                shape =
                    RoundedCornerShape(12.dp),

                color =
                    AzulClaro
            ) {

                Text(
                    text = producto.categoria,

                    modifier = Modifier.padding(
                        horizontal = 12.dp,
                        vertical = 6.dp
                    ),

                    style =
                        MaterialTheme.typography.labelLarge,

                    fontWeight =
                        FontWeight.SemiBold,

                    color =
                        AzulAppetive
                )
            }

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            // PRECIO

            Text(
                text =
                    "$${formatoPrecio.format(producto.precio)}",

                style =
                    MaterialTheme.typography.headlineMedium,

                fontWeight =
                    FontWeight.Bold,

                color =
                    AzulAppetive
            )

            Spacer(
                modifier = Modifier.height(18.dp)
            )

            // DESCRIPCIÓN

            Card(
                modifier =
                    Modifier.fillMaxWidth(),

                shape =
                    RoundedCornerShape(20.dp),

                colors =
                    CardDefaults.cardColors(
                        containerColor = Color.White
                    ),

                elevation =
                    CardDefaults.cardElevation(
                        defaultElevation = 2.dp
                    )
            ) {

                Column(
                    modifier = Modifier.padding(16.dp)
                ) {

                    Text(
                        text = "Descripción",

                        style =
                            MaterialTheme.typography.titleMedium,

                        fontWeight =
                            FontWeight.Bold,

                        color =
                            TextoPrincipal
                    )

                    Spacer(
                        modifier = Modifier.height(8.dp)
                    )

                    Text(
                        text = producto.descripcion,

                        style =
                            MaterialTheme.typography.bodyLarge,

                        color =
                            TextoSecundario
                    )
                }
            }
        }

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        // BOTÓN AGREGAR AL CARRITO
        Surface(
            onClick = onAgregarCarrito,

            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),

            shape =
                RoundedCornerShape(18.dp),

            color =
                AzulAppetive,

            shadowElevation = 5.dp
        ) {

            Row(
                modifier = Modifier.fillMaxSize(),

                horizontalArrangement =
                    Arrangement.Center,

                verticalAlignment =
                    Alignment.CenterVertically
            ) {

                Icon(
                    imageVector =
                        Icons.Default.ShoppingCart,

                    contentDescription = null,

                    tint = Color.White,

                    modifier =
                        Modifier.size(22.dp)
                )

                Spacer(
                    modifier = Modifier.size(9.dp)
                )

                Text(
                    text = "Agregar al carrito",

                    style =
                        MaterialTheme.typography.titleMedium,

                    fontWeight =
                        FontWeight.Bold,

                    color = Color.White
                )
            }
        }
    }
}