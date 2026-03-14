package com.example.appurale.modelo

data class Tarea(
    val nombre: String,
    val inicio: Long,
    val fin: Long,
    val intervaloVibracion: Long,
    val sonidoNotificacionFinal: String
)