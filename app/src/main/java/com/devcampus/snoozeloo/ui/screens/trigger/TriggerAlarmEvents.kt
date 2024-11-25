package com.devcampus.snoozeloo.ui.screens.trigger

import com.devcampus.snoozeloo.core.UIEvent
import java.util.UUID

sealed class TriggerAlarmEvents : UIEvent {
    override val key: UUID = UUID.randomUUID()
    data class SetupAlarm(val isAlarmOn: Boolean) : TriggerAlarmEvents()
}