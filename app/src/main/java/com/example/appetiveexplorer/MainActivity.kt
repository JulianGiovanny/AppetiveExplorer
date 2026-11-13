
package com.example.appetiveexplorer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import com.example.appetiveexplorer.ui.screens.CafeteriaScreen
import com.example.appetiveexplorer.ui.screens.HomeScreen
import com.example.appetiveexplorer.ui.theme.AppetiveExplorerTheme
import com.example.appetiveexplorer.ui.viewmodel.CarritoViewModel

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val carritoViewModel =
            ViewModelProvider(this)[CarritoViewModel::class.java]

        setContent {
            AppetiveExplorerTheme {
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    var cafeteriaSeleccionada by remember {
                        mutableStateOf<Int?>(null)
                    }

                    // Si estamos dentro de una cafetería.
                    if (cafeteriaSeleccionada != null) {

                        BackHandler {
                            cafeteriaSeleccionada = null
                        }

                        HomeScreen(
                            cafeteriaId = cafeteriaSeleccionada!!,
                            onCambiarCafeteria = {
                                cafeteriaSeleccionada = null
                            },
                            carritoViewModel = carritoViewModel
                        )
                    } else {

                        CafeteriaScreen(
                            onCafeteriaSelected = { cafeteriaId ->
                                cafeteriaSeleccionada = cafeteriaId
                            }
                        )
                    }
                }
            }
        }
    }
}
