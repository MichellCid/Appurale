package com.example.appurale.pantallas

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.appurale.utilidades.RelojPomodoro
import com.example.appurale.viewmodel.TareasViewModel
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun PantallaDetalles(
    navController: NavController,
    index: Int,
    viewModel: TareasViewModel
) {

    if (!viewModel.tareaValida(index)) {
        Text("Tarea no válida")
        return
    }

    val tarea = viewModel.obtenerTarea(index)

    var mostrarDialogo by remember { mutableStateOf(false) }

    val tiempoTotal = ((tarea.fin - tarea.inicio) / 1000).toInt()

    var tiempoRestante by remember {
        mutableStateOf(
            ((tarea.fin - System.currentTimeMillis()) / 1000).toInt()
        )
    }

    if (tiempoRestante < 0) tiempoRestante = 0

    var pausado by remember { mutableStateOf(false) }
    var finalizado by remember { mutableStateOf(false) }


    LaunchedEffect(pausado, finalizado) {

        while (tiempoRestante > 0 && !pausado && !finalizado) {
            delay(1000)
            tiempoRestante--
        }

        if (tiempoRestante <= 0) {
            finalizado = true
            mostrarDialogo = true
        }
    }

    Column(

        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            IconButton(onClick = {
                navController.popBackStack()
            }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Regresar"
                )
            }
        }

        Text(
            text = tarea.nombre,
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(30.dp))

        MostrarHoras(tarea.inicio, tarea.fin)

        Spacer(modifier = Modifier.height(30.dp))


        RelojPomodoro(
            tiempoRestante = tiempoRestante,
            tiempoTotal = tiempoTotal
        )

        Spacer(modifier = Modifier.height(30.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {

            Button(onClick = {
                pausado = !pausado
            }) {
                Text(if (pausado) "Reanudar" else "Pausar")
            }

            Button(onClick = {
                finalizado = true
                navController.popBackStack()
            }) {
                Text("Finalizar")
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        if (finalizado) {

            Button(onClick = {
                tiempoRestante = tiempoTotal
                pausado = false
                finalizado = false
            }) {
                Text("Reiniciar")
            }
        }

        if (mostrarDialogo) {

            AlertDialog(
                onDismissRequest = { },

                title = {
                    Text("Tiempo terminado")
                },

                text = {
                    Text("La actividad ha finalizado")
                },

                confirmButton = {

                    Button(
                        onClick = {
                            mostrarDialogo = false
                        }
                    ) {
                        Text("Aceptar")
                    }
                }
            )
        }
    }
}

@Composable
fun MostrarHoras(inicio: Long, fin: Long) {
    val formato = SimpleDateFormat("HH:mm:ss", Locale.getDefault())

    Column {
        Text(text = "Inicio: ${formato.format(Date(inicio))}",
                style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(30.dp))

        Text(text = "Fin: ${formato.format(Date(fin))}",
            style = MaterialTheme.typography.headlineMedium)
    }
}