package com.example.appurale.utilidades

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun RelojPomodoro(
    tiempoRestante: Int,
    tiempoTotal: Int
) {

    val progreso = tiempoRestante / tiempoTotal.toFloat()

    val progresoAnimado by animateFloatAsState(
        targetValue = progreso,
        label = ""
    )

    val color = when {
        progreso > 0.5f -> Color(0xFF4CAF50)
        progreso > 0.2f -> Color(0xFFFFC107)
        else -> Color(0xFFF44336)
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.size(250.dp)
    ) {

        Canvas(modifier = Modifier.size(250.dp)) {

            val stroke = 25f

            drawCircle(
                color = Color.LightGray,
                style = Stroke(width = stroke)
            )

            drawArc(
                color = color,
                startAngle = -90f,
                sweepAngle = 360 * progresoAnimado,
                useCenter = false,
                style = Stroke(width = stroke)
            )
        }

        Text(
            text = formatearTiempo(tiempoRestante),
            fontSize = 36.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

fun formatearTiempo(segundos: Int): String {

    val min = segundos / 60
    val sec = segundos % 60

    return "%02d:%02d".format(min, sec)
}