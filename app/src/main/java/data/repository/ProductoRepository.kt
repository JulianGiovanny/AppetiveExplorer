package com.example.appetiveexplorer.data.repository

import com.example.appetiveexplorer.R
import com.example.appetiveexplorer.data.model.Producto

class ProductoRepository {

    fun obtenerProductos(): List<Producto> {

        return listOf(

            Producto(
                id = 1,
                nombre = "Café",
                categoria = "Bebidas",
                precio = 4000,
                imagen = R.drawable.cafe,
                descripcion = "Café caliente preparado para disfrutar en cualquier momento del día."
            ),

            Producto(
                id = 2,
                nombre = "Chocolate",
                categoria = "Bebidas",
                precio = 5000,
                imagen = R.drawable.chocolate,
                descripcion = "Chocolate caliente, ideal para acompañar tus momentos de descanso."
            ),

            Producto(
                id = 3,
                nombre = "Arepa",
                categoria = "Harinas",
                precio = 3500,
                imagen = R.drawable.arepa,
                descripcion = "Arepa tradicional, perfecta para disfrutar como acompañamiento o merienda."
            ),

            Producto(
                id = 4,
                nombre = "Brownie",
                categoria = "Dulces",
                precio = 4500,
                imagen = R.drawable.brownie,
                descripcion = "Brownie de textura suave y sabor dulce, ideal para acompañar una bebida."
            ),

            Producto(
                id = 5,
                nombre = "Dona",
                categoria = "Harinas",
                precio = 5500,
                imagen = R.drawable.dona,
                descripcion = "Dona dulce y suave, una opción perfecta para una merienda."
            ),

            Producto(
                id = 6,
                nombre = "Pastel de pollo",
                categoria = "Harinas",
                precio = 2500,
                imagen = R.drawable.pastel_pollo,
                descripcion = "Pastel relleno de pollo, una opción práctica y deliciosa para cualquier momento."
            )
        )
    }
}