package com.example.appurale.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.appurale.modelo.Tarea

class TareasViewModel : ViewModel() {

    var tareas = mutableStateListOf<Tarea>()
        private set

    fun agregarTarea(
        nombre: String,
        inicio: Long,
        fin: Long,
        intervalo: Long
    ) {

        tareas.add(
            Tarea(
                nombre,
                inicio,
                fin,
                intervalo,
                "default"
            )
        )
    }

    fun actualizarTarea(
        index: Int,
        nombre: String,
        inicio: Long,
        fin: Long,
        intervalo: Long
    ) {

        tareas[index] = Tarea(
            nombre,
            inicio,
            fin,
            intervalo,
            "default"
        )
    }

}
