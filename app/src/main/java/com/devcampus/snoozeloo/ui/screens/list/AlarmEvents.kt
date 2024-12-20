package com.devcampus.snoozeloo.ui.screens.list

import com.devcampus.snoozeloo.core.UIEvent
import com.devcampus.snoozeloo.dto.AlarmEntity
import java.util.UUID

sealed class AlarmEvents : UIEvent {
    override val key: UUID = UUID.randomUUID()
    data class ToggleAlarmEvent(val alarm: AlarmEntity) : AlarmEvents()
}