package com.devcampus.snoozeloo.ui.screens.trigger

import com.devcampus.snoozeloo.core.BaseViewModel
import com.devcampus.snoozeloo.dto.AlarmEntity
import com.devcampus.snoozeloo.ui.screens.trigger.AlarmTriggerViewModel.AlarmTriggerState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AlarmTriggerViewModel @Inject constructor() : BaseViewModel<AlarmTriggerState>() {

    data class AlarmTriggerState(
        val alarms: AlarmEntity,
    )
}