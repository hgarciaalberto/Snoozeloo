package com.devcampus.snoozeloo.receivers

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.devcampus.snoozeloo.dto.AlarmEntity

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        // Aquí puedes manejar el evento de la alarma
        val alarm = intent.getSerializableExtra("alarm")
        showNotification(context, alarm)
    }

    private fun showNotification(context: Context, alarm: AlarmEntity) {
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Crear canal de notificación (solo necesario en Android 8+)
        val channel = NotificationChannel(
            "alarm_channel",
            "Alarm Notifications",
            NotificationManager.IMPORTANCE_HIGH
        )
        notificationManager.createNotificationChannel(channel)

        // Crear la notificación
        val notification = NotificationCompat.Builder(context, "alarm_channel")
//            .setSmallIcon(R.drawable.ic_alarm)
//            .setContentTitle("¡Alarma!")
            .setContentText("Es hora de: ${alarm?.label ?: "tu evento"}")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .build()

        // Mostrar la notificación
        notificationManager.notify(1, notification)
    }
}