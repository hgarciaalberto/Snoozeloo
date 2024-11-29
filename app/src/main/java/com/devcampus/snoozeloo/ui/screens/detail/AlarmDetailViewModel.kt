package com.devcampus.snoozeloo.ui.screens.detail

import com.devcampus.snoozeloo.core.BaseViewModel
import com.devcampus.snoozeloo.core.CommonUiEvent
import com.devcampus.snoozeloo.core.State
import com.devcampus.snoozeloo.core.UIEvent
import com.devcampus.snoozeloo.core.UiState
import com.devcampus.snoozeloo.dto.AlarmEntity
import com.devcampus.snoozeloo.repository.room.AlarmDao
import com.devcampus.snoozeloo.usecases.SetAlarmUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.firstOrNull
import timber.log.Timber
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class AlarmDetailViewModel @Inject constructor(
    private val alarmDao: AlarmDao,
    private val setAlarmUseCase: SetAlarmUseCase,
) : BaseViewModel<AlarmDetailState>(
    defaultState = UiState(
        state = State.Loading(),
        data = AlarmDetailState()
    )
) {

    override fun handleEvent(event: UIEvent) {
        when (event) {
            is AlarmDetailEvent.ChangeAlarmNameEvent -> {
                emitStateCopy {
                    it?.copy(
                        label = event.label,
                        isDialogVisible = false
                    )
                }
            }

            is AlarmDetailEvent.DeleteAlarmEvent -> {
                launch {
                    alarmDao.deleteAlarm(event.alarm)
                }
                emitEvent(CommonUiEvent.NavigationEvent.NavigateBack)
            }


            is AlarmDetailEvent.SaveAlarmEvent -> {
                launch {
                    val alarm = addAlarm(
                        alarmId = event.alarm?.id ?: -1,
                        hour = event.hour,
                        minute = event.minute,
                        label = state.value.data?.label ?: ""
                    )
                    setAlarmUseCase(alarm)
                }
                emitEvent(CommonUiEvent.NavigationEvent.NavigateBack)
            }

            is AlarmDetailEvent.ChangeLabelDialogVisibilityEvent -> {
                emitStateCopy {
                    it?.copy(
                        isDialogVisible = event.isVisible
                    )
                }
            }

            else -> {
                super.handleEvent(event)
            }
        }
    }

    fun setupAlarm(alarm: AlarmEntity?) {
        emitStateCopy {
            it?.copy(
                label = alarm?.label,
            )
        }
    }

    private suspend fun addAlarm(alarmId: Int, hour: Int, minute: Int, label: String): AlarmEntity? {

        var alarm = AlarmEntity(
            label = label,
            time = Calendar.getInstance().apply {
                time = time
                set(Calendar.HOUR_OF_DAY, hour)
                set(Calendar.MINUTE, minute)
                set(Calendar.SECOND, 0)
            }.time,
            repeat = "",
            enabled = true,
        )

        // Set up the id only if it is != -1 (existing alarm)
        if (alarmId > 0) {
            alarm = alarm.copy(id = alarmId)
        }

        // Check the id of the alarm already exists in the database to insert or update the alarm
        val existingAlarm = alarmDao.getAlarmById(alarm.id).firstOrNull()
        if (existingAlarm == null || existingAlarm.id == -1) {
            // Insert the new alarm
            Timber.d("Inserting alarm: $alarm")
            alarmDao.insertAlarm(alarm).also { newId ->
                alarm = alarm.copy(id = newId.toInt()) // Set up the id of the new alarm
            }
        } else {
            // Update the existing alarm
            Timber.d("Updating alarm: $alarm")
            alarmDao.updateAlarm(alarm)
        }

        return alarm
    }
}
