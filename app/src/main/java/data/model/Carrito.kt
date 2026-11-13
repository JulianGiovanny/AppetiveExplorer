package com.example.appetiveexplorer.data.model

data class Carrito(
    val producto: Producto,
    val cantidad: Int = 1
) {
    val subtotal: Int
        get() = producto.precio * cantidad
}