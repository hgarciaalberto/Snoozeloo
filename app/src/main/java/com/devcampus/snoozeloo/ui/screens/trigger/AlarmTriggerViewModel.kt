package com.devcampus.snoozeloo.ui.screens.trigger

import android.media.RingtoneManager
import com.devcampus.snoozeloo.core.BaseViewModel
import com.devcampus.snoozeloo.core.State
import com.devcampus.snoozeloo.core.UIEvent
import com.devcampus.snoozeloo.core.UiState
import com.devcampus.snoozeloo.ui.screens.trigger.AlarmTriggerViewModel.AlarmTriggerState
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class AlarmTriggerViewModel @Inject constructor() : BaseViewModel<AlarmTriggerState>(
    defaultState = UiState(
        state = State.Loading(),
        data = AlarmTriggerState()
    )
) {

    override fun handleEvent(event: UIEvent) {
        when (event) {
            is TriggerAlarmEvents.SetupAlarm -> setupAlarm(event.isAlarmOn)
            else -> super.handleEvent(event)
        }
    }

    fun setupAlarm(isAlarmOn: Boolean) {
        Timber.d("Set Alarm: $isAlarmOn")
        emitStateCopy {
            it?.copy(isAlarmOn = isAlarmOn)
        }
    }

    data class AlarmTriggerState(
        val isAlarmOn: Boolean = true,
    )

    companion object {
        val URI_RINGTONE = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
    }
}