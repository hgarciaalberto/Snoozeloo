package com.devcampus.snoozeloo.receivers

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import androidx.core.app.NotificationCompat
import com.devcampus.snoozeloo.MainActivity
import com.devcampus.snoozeloo.R
import com.devcampus.snoozeloo.dto.AlarmEntity
import timber.log.Timber

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        Timber.tag("alarm").d("Alarm received!")
        val alarm = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra("alarm", AlarmEntity::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra<AlarmEntity>("alarm")
        }
        showNotification(context, alarm)
    }

    private fun showNotification(context: Context, alarm: AlarmEntity?) {
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Create notification channel (only necessary on Android 8+)
        val channel = NotificationChannel(
            "alarm_channel",
            "Alarm Notifications",
            NotificationManager.IMPORTANCE_HIGH
        )
        notificationManager.createNotificationChannel(channel)


        // Create an Intent to open AlarmTriggerScreen
        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            putExtra("alarm", alarm)
        }

        // Create a PendingIntent
        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        // Create the notification
        val notification = NotificationCompat.Builder(context, "alarm_channel")
            .setContentTitle("SnoozeLoo Alarm")
            .setContentText(alarm?.label ?: "")
            .setSmallIcon(R.drawable.baseline_alarm_24)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM))
            .setContentIntent(pendingIntent)
            .build()

        // Show notification
        notificationManager.notify(1, notification)
    }
}