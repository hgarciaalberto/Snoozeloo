package com.devcampus.snoozeloo.ui.screens.detail

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.devcampus.snoozeloo.MainActivity
import com.devcampus.snoozeloo.core.BaseViewModel
import com.devcampus.snoozeloo.core.CommonUiEvent
import com.devcampus.snoozeloo.core.State
import com.devcampus.snoozeloo.core.UIEvent
import com.devcampus.snoozeloo.core.UiState
import com.devcampus.snoozeloo.dto.AlarmEntity
import com.devcampus.snoozeloo.repository.room.AlarmDao
import dagger.hilt.android.lifecycle.HiltViewModel
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
                        label = event.label
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
        val alarmIntent = Intent(context, AlarmReceiver::class.java).apply {
            putExtra("alarm", alarm)
        }

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            0,
            alarmIntent,
            PendingIntent.FLAG_IMMUTABLE
        )

        alarmMgr.set(
            AlarmManager.ELAPSED_REALTIME_WAKEUP,
            alarm.time.time,
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

    private suspend fun addAlarm(hour: Int, minute: Int, label: String): AlarmEntity? {

        val alarm = AlarmEntity(
            label = label,
            time = Calendar.getInstance().apply {
                time = time
                set(Calendar.HOUR_OF_DAY, hour)
                set(Calendar.MINUTE, minute)
            }.time,
            repeat = "",
            enabled = true,
        )

        alarmDao.insertAlarm(alarm)

        return alarm
    }
}
