package com.devcampus.snoozeloo.ui.screens.detail

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.devcampus.snoozeloo.core.BaseViewModel
import com.devcampus.snoozeloo.core.CommonUiEvent
import com.devcampus.snoozeloo.core.State
import com.devcampus.snoozeloo.core.UIEvent
import com.devcampus.snoozeloo.core.UiState
import com.devcampus.snoozeloo.dto.AlarmEntity
import com.devcampus.snoozeloo.receivers.AlarmReceiver
import com.devcampus.snoozeloo.repository.room.AlarmDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.firstOrNull
import timber.log.Timber
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class AlarmDetailViewModel @Inject constructor(
    private val alarmDao: AlarmDao,
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
                    setAlarmInSystem(event.context, alarm)
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

    fun setAlarmInSystem(context: Context, alarm: AlarmEntity?) {
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

        Timber.tag("alarm").d("millis til alarm: ${alarm.time.time.minus(Calendar.getInstance().timeInMillis)}")
        alarmMgr.setRepeating(
            AlarmManager.RTC_WAKEUP,
            alarm.time.time.minus(
                Calendar.getInstance().apply {
                    set(Calendar.SECOND, 0)
                }.timeInMillis
            ),
            AlarmManager.INTERVAL_DAY,
            pendingIntent
        )
    }

    fun setupAlarm(alarm: AlarmEntity?) {
        emitStateCopy {
            it?.copy(
                label = alarm?.label,
            )
        }
    }

    private suspend fun addAlarm(alarmId: Int, hour: Int, minute: Int, label: String): AlarmEntity? {

        val alarm = AlarmEntity(
            id = alarmId,
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

        // Check the id of the alarm already exists in the database to insert or update the alarm
        val existingAlarm = alarmDao.getAlarmById(alarm.id).firstOrNull()
        if (existingAlarm != null) {
            // Update the existing alarm
            Timber.d("Updating alarm: $alarm")
            alarmDao.updateAlarm(alarm)
        } else {
            // Insert the new alarm
            Timber.d("Inserting alarm: $alarm")
            alarmDao.insertAlarm(alarm)
        }

        return alarm
    }
}
