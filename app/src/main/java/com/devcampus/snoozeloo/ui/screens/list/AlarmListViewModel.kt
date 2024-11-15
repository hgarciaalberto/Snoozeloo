package com.devcampus.snoozeloo.ui.screens.list

import android.annotation.SuppressLint
import android.icu.text.SimpleDateFormat
import com.devcampus.snoozeloo.core.BaseViewModel
import com.devcampus.snoozeloo.core.State.Loading
import com.devcampus.snoozeloo.dto.AlarmEntity
import com.devcampus.snoozeloo.repository.room.AlarmDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class AlarmListViewModel @Inject constructor(
    private val alarmDao: AlarmDao
) : BaseViewModel<AlarmListViewModel.AlarmListState>() {

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

    fun toggleAlarm(alarm: AlarmEntity) = launch {
        emitStateCopySuspend(newState = Loading())
        delay(1000) // Just for testing purposes
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
                time = SimpleDateFormat("hh:mm a", Locale.getDefault()).parse("07:00 AM")!!,
                enabled = true,
            ),
            AlarmEntity().copy(
                id = 1,
                label = "Go to bed",
                time = SimpleDateFormat("hh:mm a", Locale.getDefault()).parse("08:00 PM")!!,
                enabled = true,
            ),
            AlarmEntity().copy(
                id = 2,
                label = "Education",
                time = SimpleDateFormat("hh:mm a", Locale.getDefault()).parse("09:00 AM")!!,
                enabled = false,
            ),
            AlarmEntity().copy(
                id = 3,
                label = "Dinner",
                time = SimpleDateFormat("hh:mm a", Locale.getDefault()).parse("10:00 PM")!!,
                enabled = false,
            ),
            AlarmEntity().copy(
                id = 4,
                label = "Education",
                time = SimpleDateFormat("hh:mm a", Locale.getDefault()).parse("09:00 AM")!!,
                enabled = false,
            ),
            AlarmEntity().copy(
                id = 5,
                label = "Dinner",
                time = SimpleDateFormat("hh:mm a", Locale.getDefault()).parse("10:00 PM")!!,
                enabled = false,
            ),
            AlarmEntity().copy(
                id = 6,
                label = "Education",
                time = SimpleDateFormat("hh:mm a", Locale.getDefault()).parse("09:00 AM")!!,
                enabled = false,
            ),
            AlarmEntity().copy(
                id = 7,
                label = "Dinner",
                time = SimpleDateFormat("hh:mm a", Locale.getDefault()).parse("10:00 PM")!!,
                enabled = false,
            ),
        )
    }
}
