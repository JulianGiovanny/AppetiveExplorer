package com.example.appetiveexplorer.ui.components

import androidx.compose.material3.FilterChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun CategoryChip(
    nombre: String,
    seleccionada: Boolean,
    onClick: () -> Unit
) {

    FilterChip(
        selected = seleccionada,
        onClick = onClick,
        label = {
            Text(text = nombre)
        }
    )
}