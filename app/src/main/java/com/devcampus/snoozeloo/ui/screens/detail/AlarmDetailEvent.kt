package com.devcampus.snoozeloo.ui.screens.detail

import com.devcampus.snoozeloo.core.UIEvent
import com.devcampus.snoozeloo.dto.AlarmEntity
import java.util.UUID
import java.util.UUID.randomUUID

sealed class AlarmDetailEvent : UIEvent {
    override val key: UUID = randomUUID()

    data class ChangeHourEvent(val hour: String) : AlarmDetailEvent()
    data class ChangeMinuteEvent(val minute: String) : AlarmDetailEvent()
    data class ChangeAlarmNameEvent(val name: String) : AlarmDetailEvent()
    data class ChangeLabelDialogVisibility(val isVisible: Boolean) : AlarmDetailEvent()
    data class SaveAlarm(val alarm: AlarmEntity) : AlarmDetailEvent()
}