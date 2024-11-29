package com.devcampus.snoozeloo.usecases

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.devcampus.snoozeloo.dto.AlarmEntity
import com.devcampus.snoozeloo.receivers.AlarmReceiver
import timber.log.Timber
import java.util.Calendar

class SetAlarmUseCase(val context: Context) {
    operator fun invoke(alarm: AlarmEntity?) {
        if (alarm == null) return

        var alarmMgr: AlarmManager? = null

        alarmMgr = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmReceiver::class.java).apply {
            putExtra("alarm", alarm)
        }

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            alarm.id, // Use same id that is used to schedule the alarm to cancel it
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        Timber.tag("alarm").d("Alarm scheduled for: ${alarm.time}, current time: ${Calendar.getInstance().time}")
        val timeDifferenceInSeconds = (alarm.time.time - Calendar.getInstance().timeInMillis) / 1000
        Timber.tag("alarm").d("Time difference in seconds: $timeDifferenceInSeconds")

        alarmMgr.setRepeating(
            AlarmManager.RTC_WAKEUP,
            alarm.time.time,
            AlarmManager.INTERVAL_DAY,
            pendingIntent
        )
    }

    fun cancelAlarm(alarm: AlarmEntity) {
        val alarmMgr = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmReceiver::class.java).apply {
            putExtra("alarm", alarm)
        }

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            alarm.id,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        Timber.tag("alarm").d("Canceling alarm with ID: ${alarm.id}")
        alarmMgr.cancel(pendingIntent) // Cancela la alarma programada
    }
}