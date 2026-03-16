package com.example.appurale.pantallas

import android.app.TimePickerDialog
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.appurale.viewmodel.TareasViewModel
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaAgregar(
    navController: NavController,
    index: Int,
    viewModel: TareasViewModel
) {

    val context = LocalContext.current

    var nombre by remember { mutableStateOf("") }

    var inicio by remember {
        mutableStateOf(System.currentTimeMillis())
    }

    var fin by remember {
        mutableStateOf(System.currentTimeMillis())
    }

    var intervalo by remember { mutableStateOf(5L) }

    val formato = SimpleDateFormat("HH:mm", Locale.getDefault())

    val opcionesIntervalo = listOf(3L, 5L, 10L, 15L)

    var expandIntervalo by remember { mutableStateOf(false) }


    LaunchedEffect(Unit) {

        if (index >= 0) {

            val t = viewModel.tareas[index]

            nombre = t.nombre
            inicio = t.inicio
            fin = t.fin
            intervalo = t.intervaloVibracion
        }
    }


    fun abrirHoraInicio() {

        val cal = Calendar.getInstance()

        TimePickerDialog(
            context,
            { _, h, m ->

                val c = Calendar.getInstance()

                c.set(Calendar.HOUR_OF_DAY, h)
                c.set(Calendar.MINUTE, m)

                inicio = c.timeInMillis
            },
            cal.get(Calendar.HOUR_OF_DAY),
            cal.get(Calendar.MINUTE),
            true
        ).show()
    }


    fun abrirHoraFin() {

        val cal = Calendar.getInstance()

        TimePickerDialog(
            context,
            { _, h, m ->

                val c = Calendar.getInstance()

                c.set(Calendar.HOUR_OF_DAY, h)
                c.set(Calendar.MINUTE, m)

                fin = c.timeInMillis
            },
            cal.get(Calendar.HOUR_OF_DAY),
            cal.get(Calendar.MINUTE),
            true
        ).show()
    }



    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFEFEFEF))
            .padding(20.dp)
    ) {

        Button(
            onClick = {
                navController.popBackStack()
            }
        ) {
            Text("Regresar")
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            if (index == -1)
                "AGREGAR ACTIVIDAD"
            else
                "EDITAR ACTIVIDAD",

            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(20.dp))


        OutlinedTextField(
            value = nombre,
            onValueChange = { nombre = it },
            label = { Text("Nombre") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(20.dp))


        Button(
            onClick = { abrirHoraInicio() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Seleccionar hora inicio")
        }

        Text(
            "Inicio: ${formato.format(Date(inicio))}"
        )


        Spacer(modifier = Modifier.height(10.dp))



        Button(
            onClick = { abrirHoraFin() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Seleccionar hora fin")
        }

        Text(
            "Fin: ${formato.format(Date(fin))}"
        )


        Spacer(modifier = Modifier.height(20.dp))


        ExposedDropdownMenuBox(
            expanded = expandIntervalo,
            onExpandedChange = {
                expandIntervalo = !expandIntervalo
            }
        ) {

            OutlinedTextField(
                value = "$intervalo minutos",
                onValueChange = {},
                readOnly = true,
                label = { Text("Intervalo") },
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth()
            )

            ExposedDropdownMenu(
                expanded = expandIntervalo,
                onDismissRequest = {
                    expandIntervalo = false
                }
            ) {

                opcionesIntervalo.forEach {

                    DropdownMenuItem(
                        text = {
                            Text("$it minutos")
                        },
                        onClick = {

                            intervalo = it
                            expandIntervalo = false
                        }
                    )
                }
            }
        }


        Spacer(modifier = Modifier.height(30.dp))


        Button(
            onClick = {

                if (index == -1) {

                    viewModel.agregarTarea(
                        nombre,
                        inicio,
                        fin,
                        intervalo
                    )

                } else {

                    viewModel.actualizarTarea(
                        index,
                        nombre,
                        inicio,
                        fin,
                        intervalo
                    )
                }

                navController.popBackStack()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Guardar")
        }

    }
}
