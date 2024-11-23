package com.devcampus.snoozeloo.extensions

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.devcampus.snoozeloo.core.BaseViewModel
import com.devcampus.snoozeloo.core.CommonUiEvent
import com.devcampus.snoozeloo.core.UIEvent
import com.devcampus.snoozeloo.core.handleEvent
import java.util.Calendar
import java.util.Date
import java.util.concurrent.TimeUnit

@Composable
fun <T> BaseViewModel<T>.HandleEvents(navController: NavController) {
    val eventsData by this.events.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = eventsData.key) {
        eventsData.events.forEach { event ->
            if (event !is CommonUiEvent.Unknown) {
                handleEvent(
                    composeViewModel = this@HandleEvents,
                    navController = navController,
                    event = event
                )
            }
            this@HandleEvents.removeEvent(event)
        }
    }
}

/**
 * Extension function that calculates the next alarm date
 * If `useWeekday` is true, the next alarm is set for the same weekday as the original alarm
 * If `useWeekday` is false, the alarm is set for the next occurrence of the same time
 */
fun Date.nextAlarmDate(useWeekday: Boolean = true): Date {
    val alarmCalendar = Calendar.getInstance().apply {
        time = this@nextAlarmDate // Set the calendar time to this date instance
    }

    val currentCalendar = Calendar.getInstance() // Get the current date and time
    var daysUntilNext = 0 // Default to zero days until the next alarm

    if (useWeekday) {
        // Get the day of the week for the alarm
        val dayOfWeek = alarmCalendar.get(Calendar.DAY_OF_WEEK)
        // Calculate days until the next occurrence on the same weekday
        daysUntilNext = (dayOfWeek - currentCalendar.get(Calendar.DAY_OF_WEEK) + 7) % 7

        // If the calculated day is today but the time has already passed, add 7 days to make it next week
        if (daysUntilNext == 0 && alarmCalendar.timeInMillis < currentCalendar.timeInMillis) {
            daysUntilNext = 7
        }
        currentCalendar.add(Calendar.DAY_OF_YEAR, daysUntilNext) // Add the days to the current date
    } else {
        // If not using weekday, check if the alarm time has already passed today
        if (alarmCalendar.timeInMillis < currentCalendar.timeInMillis) {
            // If it has, add 1 day to set it for tomorrow
            currentCalendar.add(Calendar.DAY_OF_YEAR, 1)
        }
    }

    // Set the time in currentCalendar to match the time of `alarmCalendar`
    currentCalendar.set(Calendar.HOUR_OF_DAY, alarmCalendar.get(Calendar.HOUR_OF_DAY))
    currentCalendar.set(Calendar.MINUTE, alarmCalendar.get(Calendar.MINUTE))
    currentCalendar.set(Calendar.SECOND, 0)
    currentCalendar.set(Calendar.MILLISECOND, 0)

    return currentCalendar.time // Return the calculated next alarm date
}

/**
 * Extension function that formats the time remaining until a given alarm date
 * It calculates the difference between the current time and `alarmDate`
 */
fun Date.formatTimeUntil(): String {
    val currentDate = Date() // Get the current date and time
    val diffInMillis = this.time - currentDate.time // Calculate the time difference in milliseconds

    // Convert the difference to days, hours, and minutes
    val days = TimeUnit.MILLISECONDS.toDays(diffInMillis)
    val hours = TimeUnit.MILLISECONDS.toHours(diffInMillis) % 24
    val minutes = TimeUnit.MILLISECONDS.toMinutes(diffInMillis) % 60

    // Format the result based on the remaining time
    return when {
        days > 0 -> "Alarm in ${days}d ${hours}h ${minutes}min" // Shows days, hours, and minutes if there are days
        hours > 0 -> "Alarm in ${hours}h ${minutes}min"         // Shows hours and minutes if there are no days
        else -> "Alarm in ${minutes}min"                        // Shows only minutes if less than an hour
    }
}