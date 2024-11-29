package com.devcampus.snoozeloo.ui.screens.list

import android.annotation.SuppressLint
import com.devcampus.snoozeloo.core.BaseViewModel
import com.devcampus.snoozeloo.core.State.Loading
import com.devcampus.snoozeloo.core.UIEvent
import com.devcampus.snoozeloo.core.UiState
import com.devcampus.snoozeloo.dto.AlarmEntity
import com.devcampus.snoozeloo.repository.room.AlarmDao
import com.devcampus.snoozeloo.usecases.SetAlarmUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class AlarmListViewModel @Inject constructor(
    private val alarmDao: AlarmDao,
    private val setAlarmUseCase: SetAlarmUseCase,
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

    fun toggleAlarm(alarm: AlarmEntity) = launch {
        emitStateCopySuspend(newState = Loading())

        val updatedAlarm = alarm.copy(enabled = !alarm.enabled)
        alarmDao.updateAlarm(updatedAlarm)

        if (updatedAlarm.enabled) {
            setAlarmUseCase(updatedAlarm) // Setup the alarm if it is enabled
        } else {
            setAlarmUseCase.cancelAlarm(updatedAlarm) // Cancel the alarm if it is disabled
        }

        emitStateCopySuspend {
            it?.copy(alarms = state.value.data?.alarms ?: emptyList())
        }
    }

    data class AlarmListState(
        val alarms: List<AlarmEntity> = emptyList<AlarmEntity>(),
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
                time = Calendar.getInstance().apply {
                    set(Calendar.HOUR_OF_DAY, 11)
                    set(Calendar.MINUTE, 0)
                    set(Calendar.SECOND, 0)
                }.time,
                enabled = true,
            ),
            AlarmEntity().copy(
                id = 2,
                label = "Education",
                time = Calendar.getInstance().apply {
                    set(Calendar.HOUR_OF_DAY, 2)
                    set(Calendar.MINUTE, 30)
                    set(Calendar.SECOND, 0)
                }.time,
                enabled = false,
            ),
            AlarmEntity().copy(
                id = 3,
                label = "Dinner",
                time = Calendar.getInstance().apply {
                    set(Calendar.HOUR_OF_DAY, 13)
                    set(Calendar.MINUTE, 45)
                    set(Calendar.SECOND, 0)
                }.time,
                enabled = false,
            ),
        )
    }
}
