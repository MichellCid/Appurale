package com.example.appurale.utilidades

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log

class AlarmaProgramador(private val context: Context) {
    private val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    fun programarVibracion(minutos: Int) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            if (!alarmManager.canScheduleExactAlarms()) {
                Log.e("AlarmaProgramador", "No tiene permiso para alarmas exactas. Pidiendo al usuario...")
                solicitarPermisoExacto()
                return
            }
        }

        val intent = Intent(context, VibrationReceiver::class.java).apply {
            putExtra("intervalo", minutos)
        }

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            1001,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val tiempoMilis = System.currentTimeMillis() + (minutos * 60 * 1000L)

        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                alarmManager.setExactAndAllowWhileIdle(
                    AlarmManager.RTC_WAKEUP,
                    tiempoMilis,
                    pendingIntent
                )
            } else {
                alarmManager.setExact(AlarmManager.RTC_WAKEUP, tiempoMilis, pendingIntent)
            }
            Log.d("AlarmaProgramador", "Alarma programada con éxito en $minutos min")
        } catch (e: SecurityException) {
            Log.e("AlarmaProgramador", "Error de seguridad al programar alarma: ${e.message}")
        }
    }

    private fun solicitarPermisoExacto() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val intent = Intent(android.provider.Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM)
            context.startActivity(intent)
        }
    }
}