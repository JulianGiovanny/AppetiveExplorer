
package com.example.appetiveexplorer.ui.viewmodel

import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import com.example.appetiveexplorer.data.model.Carrito
import com.example.appetiveexplorer.data.model.Producto
import androidx.compose.runtime.mutableStateListOf
import com.example.appetiveexplorer.data.model.Pedido
class CarritoViewModel : ViewModel() {

    // Cada cafetería mantiene su propia lista de productos.
    private val carritos =
        mutableStateMapOf<Int, SnapshotStateList<Carrito>>()

    fun obtenerItems(cafeteriaId: Int): List<Carrito> {
        return carritos[cafeteriaId]?.toList() ?: emptyList()
    }

    fun agregarProducto(
        cafeteriaId: Int,
        producto: Producto
    ) {
        val items = obtenerLista(cafeteriaId)
        val indice = items.indexOfFirst {
            it.producto.id == producto.id
        }

        if (indice >= 0) {
            val existente = items[indice]
            items[indice] = existente.copy(
                cantidad = existente.cantidad + 1
            )
        } else {
            items.add(
                Carrito(
                    producto = producto,
                    cantidad = 1
                )
            )
        }
    }

    fun aumentarCantidad(
        cafeteriaId: Int,
        productoId: Int
    ) {
        val items = obtenerLista(cafeteriaId)
        val indice = items.indexOfFirst {
            it.producto.id == productoId
        }

        if (indice >= 0) {
            val item = items[indice]
            items[indice] = item.copy(
                cantidad = item.cantidad + 1
            )
        }
    }

    fun disminuirCantidad(
        cafeteriaId: Int,
        productoId: Int
    ) {
        val items = obtenerLista(cafeteriaId)
        val indice = items.indexOfFirst {
            it.producto.id == productoId
        }

        if (indice >= 0) {
            val item = items[indice]

            if (item.cantidad > 1) {
                items[indice] = item.copy(
                    cantidad = item.cantidad - 1
                )
            } else {
                items.removeAt(indice)
            }
        }
    }

    fun eliminarProducto(
        cafeteriaId: Int,
        productoId: Int
    ) {
        obtenerLista(cafeteriaId).removeAll {
            it.producto.id == productoId
        }
    }

    fun obtenerTotal(cafeteriaId: Int): Int {
        return obtenerLista(cafeteriaId).sumOf {
            it.subtotal
        }
    }

    fun obtenerCantidadTotal(cafeteriaId: Int): Int {
        return obtenerLista(cafeteriaId).sumOf {
            it.cantidad
        }
    }

    fun vaciarCarrito(cafeteriaId: Int) {
        obtenerLista(cafeteriaId).clear()
    }

    private fun obtenerLista(
        cafeteriaId: Int
    ): SnapshotStateList<Carrito> {
        return carritos.getOrPut(cafeteriaId) {
            emptyList<Carrito>().toMutableStateList()
        }
    }

    private val pedidos = mutableStateListOf<Pedido>()

    fun registrarPedido(cafeteriaId: Int): Pedido? {
        val itemsDelCarrito = obtenerItems(cafeteriaId)

        if (itemsDelCarrito.isEmpty()) {
            return null
        }

        val nuevoPedido = Pedido(
            id = (pedidos.maxOfOrNull { it.id } ?: 0) + 1,
            cafeteriaId = cafeteriaId,
            fechaMillis = System.currentTimeMillis(),
            items = itemsDelCarrito.toList(),
            total = itemsDelCarrito.sumOf { it.subtotal }
        )

        pedidos.add(nuevoPedido)
        vaciarCarrito(cafeteriaId)

        return nuevoPedido
    }

    fun obtenerPedidos(): List<Pedido> {
        return pedidos.toList()
    }

}
