package com.example.appetiveexplorer.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
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
import com.example.appetiveexplorer.R
import com.example.appetiveexplorer.data.repository.CafeteriaRepository

// COLORES DE la interfaz
private val AzulAppetive = Color(0xFF3E5C76)
private val FondoAppetive = Color(0xFFF7F4EF)
private val TextoPrincipal = Color(0xFF263746)
private val TextoSecundario = Color(0xFF66737D)



// PANTALLA DE SELECCIÓN DE CAFETERÍAS
@Composable
fun CafeteriaScreen(
    onCafeteriaSelected: (Int) -> Unit
) {

    val repository = CafeteriaRepository()
    val cafeterias = repository.obtenerCafeterias()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(FondoAppetive)
            .safeDrawingPadding()
            .padding(
                start = 20.dp,
                end = 20.dp,
                top = 20.dp,
                bottom = 16.dp
            )
    ) {

        // ENCABEZADO

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {

            Box(
                modifier = Modifier
                    .size(52.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(AzulAppetive),
                contentAlignment = Alignment.Center
            ) {

                Image(
                    painter = painterResource(
                        id = R.drawable.app
                    ),
                    contentDescription = "Logo de Appetive Explorer",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }

            Spacer(
                modifier = Modifier.size(12.dp)
            )

            Column {

                Text(
                    text = "Appetive Explorer",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = TextoPrincipal
                )

                Text(
                    text = "Tu próxima pausa empieza aquí",
                    style = MaterialTheme.typography.bodySmall,
                    color = TextoSecundario
                )
            }
        }


        Spacer(
            modifier = Modifier.height(32.dp)
        )


        // TÍTULO

        Text(
            text = "Cafeterías en Zipaquirá",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = AzulAppetive
        )

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        Text(
            text = "Selecciona una cafetería para explorar su menú y realizar tu pedido.",
            style = MaterialTheme.typography.bodyLarge,
            color = TextoSecundario
        )


        Spacer(
            modifier = Modifier.height(24.dp)
        )


        // LISTA DE CAFETERÍAS
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            items(cafeterias) { cafeteria ->

                CafeteriaCard(
                    nombre = cafeteria.nombre,
                    ciudad = cafeteria.ciudad,
                    imagen = cafeteria.imagen,

                    onClick = {
                        onCafeteriaSelected(cafeteria.id)
                    }
                )
            }
        }
    }
}



// TARJETA DE CAFETERÍA

@Composable
private fun CafeteriaCard(
    nombre: String,
    ciudad: String,
    imagen: Int,
    onClick: () -> Unit
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(125.dp)
            .clickable {
                onClick()
            },

        shape = RoundedCornerShape(24.dp),

        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),

        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        )
    ) {

        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(18.dp),

            verticalAlignment = Alignment.CenterVertically
        ) {


            // IMAGEN DE LA CAFETERÍA
            Image(
                painter = painterResource(
                    id = imagen
                ),

                contentDescription = "Imagen de $nombre",

                modifier = Modifier
                    .size(82.dp)
                    .clip(
                        RoundedCornerShape(20.dp)
                    ),

                contentScale = ContentScale.Crop
            )


            Spacer(
                modifier = Modifier.size(16.dp)
            )



            // INFORMACIÓN
            Column {

                Text(
                    text = nombre,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = TextoPrincipal
                )

                Spacer(
                    modifier = Modifier.height(4.dp)
                )

                Text(
                    text = "Cafetería · $ciudad",
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextoSecundario
                )
            }
        }
    }
}