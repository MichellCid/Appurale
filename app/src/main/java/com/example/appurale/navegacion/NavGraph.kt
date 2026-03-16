package com.example.appurale.navegacion

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.appurale.pantallas.*
import com.example.appurale.viewmodel.TareasViewModel

@Composable
fun AppNavigation() {

    val navController = rememberNavController()

    val tareasViewModel: TareasViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = "home"
    ) {

        composable("home") {
            PantallaInicio(navController)
        }


        composable("actividades") {

            PantallaActividades(
                navController = navController,
                viewModel = tareasViewModel
            )
        }



        composable(
            route = "add/{index}",

            arguments = listOf(
                navArgument("index") {
                    type = NavType.IntType
                    defaultValue = -1
                }
            )
        ) { backStackEntry ->

            val index =
                backStackEntry
                    .arguments
                    ?.getInt("index") ?: -1


            PantallaAgregar(
                navController = navController,
                index = index,
                viewModel = tareasViewModel
            )
        }



        composable("timer") {

            PantallaDetalles(
                navController = navController
            )
        }
    }
}
