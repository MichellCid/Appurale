package com.example.appurale.navegacion

import androidx.compose.runtime.Composable
import androidx.navigation.compose.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.appurale.pantallas.*


@Composable
fun AppNavigation() {

    val navController = rememberNavController()

    NavHost(navController, startDestination = "home") {

        composable("home") {
            PantallaInicio(navController)
        }

        composable("actividades") {
            PantallaActividades(navController)
        }

        composable("add") {
            PantallaAgregar(navController)
        }

        composable("timer") {
            PantallaDetalles(navController)
        }
    }
}
