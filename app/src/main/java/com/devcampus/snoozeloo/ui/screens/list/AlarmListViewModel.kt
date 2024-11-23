package com.devcampus.snoozeloo.ui.screens.list

import android.annotation.SuppressLint
import android.icu.text.SimpleDateFormat
import com.devcampus.snoozeloo.core.BaseViewModel
import com.devcampus.snoozeloo.core.State.Loading
import com.devcampus.snoozeloo.core.UIEvent
import com.devcampus.snoozeloo.core.UiState
import com.devcampus.snoozeloo.dto.AlarmEntity
import com.devcampus.snoozeloo.repository.room.AlarmDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import java.util.Calendar
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class AlarmListViewModel @Inject constructor(
    private val alarmDao: AlarmDao
) : BaseViewModel<AlarmListViewModel.AlarmListState>(
    defaultState = UiState(
        state = Loading(),
        data = AlarmListState()
    )
) {

    override fun handleEvent(event: UIEvent) {
        when (event) {
            is AlarmEvents.ToggleAlarmEvent -> toggleAlarm(event.alarm)
            else -> super.handleEvent(event)
        }

    }

    init {
        launch {
            emitLoadingSuspend()
            alarmDao.getAllAlarms().collect { alarms ->
                emitStateCopySuspend {
                    it?.copy(alarms = alarms)
                }
            }
        }
    }

//    override fun handleEvent(event: UIEvent) {
//        when(event){
//            is AlarmEvents.ToggleAlarmEvent -> {
//                toggleAlarm(event.alarm)
//            }
//            else -> super.handleEvent(event)
//        }
//    }

    fun toggleAlarm(alarm: AlarmEntity) = launch {
        emitStateCopySuspend(newState = Loading())
        delay(700) // Just for testing purposes
        val updatedAlarm = alarm.copy(enabled = !alarm.enabled)
        alarmDao.updateAlarm(updatedAlarm)
    }

    fun addAlarm() = launch {
        alarmDao.insertAlarm(
            AlarmEntity().copy(
                label = "Go to bed",
                time = SimpleDateFormat("hh:mm a", Locale.getDefault()).parse("08:00 PM")!!,
                enabled = true,
            )
        )
    }

    data class AlarmListState(
        val alarms: List<AlarmEntity> = emptyList<AlarmEntity>()
    )

    companion object {
        @SuppressLint("ConstantLocale")
        val FAKE_ALARMS = listOf(
            AlarmEntity().copy(
                id = 0,
                label = "Wake up",
                time = Calendar.getInstance().apply {
                    // Set the hour and minute directly using Calendar's set() method
                    set(Calendar.HOUR_OF_DAY, 23) // Set hour to 7 PM
                    set(Calendar.MINUTE, 46)      // Set minute to 0
                    set(Calendar.SECOND, 0)      // Set seconds to 0 to ensure accuracy

                    // Optionally, set other fields such as month and year if needed
//                    set(Calendar.MONTH, Calendar.JANUARY) // Example: January (optional)
//                    set(Calendar.DAY_OF_MONTH, 1)         // Example: Day 1 (optional)
                }.time, // Get the resulting Date object from the Calendar instance

                enabled = true,
            ),
            AlarmEntity().copy(
                id = 1,
                label = "Go to bed",
                time = Calendar.getInstance().apply{
                    set(Calendar.HOUR_OF_DAY, 11)
                    set(Calendar.MINUTE, 0)
                    set(Calendar.SECOND, 0)
                }.time,
                enabled = true,
            ),
            AlarmEntity().copy(
                id = 2,
                label = "Education",
                time = Calendar.getInstance().apply{
                    set(Calendar.HOUR_OF_DAY, 2)
                    set(Calendar.MINUTE, 30)
                    set(Calendar.SECOND, 0)
                }.time,
                enabled = false,
            ),
            AlarmEntity().copy(
                id = 3,
                label = "Dinner",
                time = Calendar.getInstance().apply{
                    set(Calendar.HOUR_OF_DAY, 13)
                    set(Calendar.MINUTE, 45)
                    set(Calendar.SECOND, 0)
                }.time,
                enabled = false,
            ),
        )
    }
}
