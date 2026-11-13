package com.example.appetiveexplorer.viewmodel

import androidx.lifecycle.ViewModel
import com.example.appetiveexplorer.data.model.Producto
import com.example.appetiveexplorer.data.repository.ProductoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class HomeViewModel : ViewModel() {

    private val repository = ProductoRepository()

    private val _productos = MutableStateFlow(
        repository.obtenerProductos()
    )

    val productos: StateFlow<List<Producto>> = _productos

    private val _categoriaSeleccionada =
        MutableStateFlow("Todos")

    val categoriaSeleccionada: StateFlow<String> =
        _categoriaSeleccionada

    fun seleccionarCategoria(categoria: String) {
        _categoriaSeleccionada.value = categoria
    }

    fun obtenerProductosFiltrados(): List<Producto> {

        val categoria = _categoriaSeleccionada.value

        return if (categoria == "Todos") {
            _productos.value
        } else {
            _productos.value.filter {
                it.categoria == categoria
            }
        }
    }
}