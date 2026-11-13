package com.example.appetiveexplorer.data.model

data class Cafeteria(
    val id: Int,
    val nombre: String,
    val ciudad: String = "Zipaquirá",
    val direccion: String,
    val horarios: List<Horario>,
    val imagen: Int
)

data class Horario(
    val dias: String,
    val horaApertura: String,
    val horaCierre: String
)