package com.example.appetiveexplorer.data.model

data class Producto(
    val id: Int,
    val nombre: String,
    val categoria: String,
    val precio: Int,
    val imagen: Int,
    val disponible: Boolean = true,
    val descripcion: String
)