
package com.example.appetiveexplorer.data.model

data class Pedido(
    val id: Int,
    val cafeteriaId: Int,
    val fechaMillis: Long,
    val items: List<Carrito>,
    val total: Int
)
