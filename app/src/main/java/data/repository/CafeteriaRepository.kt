package com.example.appetiveexplorer.data.repository

import com.example.appetiveexplorer.R
import com.example.appetiveexplorer.data.model.Cafeteria
import com.example.appetiveexplorer.data.model.Horario

class CafeteriaRepository {

    fun obtenerCafeterias(): List<Cafeteria> {

        return listOf(

            Cafeteria(
                id = 1,
                nombre = "Cafetería Uniminuto",
                ciudad = "Zipaquirá",
                direccion = "Cra. 15 #Calle 1, Sede Zipaquirá",
                horarios = listOf(
                    Horario(
                        dias = "Lunes - Sábado",
                        horaApertura = "9:00 AM",
                        horaCierre = "8:00 PM"
                    )
                ),
                imagen = R.drawable.unimi
            ),

            Cafeteria(
                id = 2,
                nombre = "Cafetería Alma",
                ciudad = "Zipaquirá",
                direccion = "Cl. 5 #13-40 Algarra 2, Zipaquirá",
                horarios = listOf(
                    Horario(
                        dias = "Martes - Jueves",
                        horaApertura = "2:30 PM",
                        horaCierre = "8:30 PM"
                    ),
                    Horario(
                        dias = "Viernes - Sábado",
                        horaApertura = "3:00 PM",
                        horaCierre = "10:00 PM"
                    ),
                    Horario(
                        dias = "Domingo",
                        horaApertura = "2:00 PM",
                        horaCierre = "8:30 PM"
                    )
                ),
                imagen = R.drawable.alma
            ),

            Cafeteria(
                id = 3,
                nombre = "Cafetería Tre23",
                ciudad = "Zipaquirá",
                direccion = "Cra. 11 #5-62 Algarra I, Zipaquirá",
                horarios = listOf(
                    Horario(
                        dias = "Lunes - Martes",
                        horaApertura = "10:00 AM",
                        horaCierre = "8:00 PM"
                    ),
                    Horario(
                        dias = "Jueves - Sábado",
                        horaApertura = "10:00 AM",
                        horaCierre = "8:00 PM"
                    ),
                    Horario(
                        dias = "Domingo",
                        horaApertura = "10:30 AM",
                        horaCierre = "7:00 PM"
                    )
                ),
                imagen = R.drawable.tres23
            ),

            Cafeteria(
                id = 4,
                nombre = "El Zipa Cafe",
                ciudad = "Zipaquirá",
                direccion = "Cra. 10 a #5 - 100, Zipaquirá",
                horarios = listOf(
                    Horario(
                        dias = "Lunes - Domingo",
                        horaApertura = "10:00 AM",
                        horaCierre = "10:00 PM"
                    )
                ),
                imagen = R.drawable.elzipa
            )
        )
    }
}