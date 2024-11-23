package com.devcampus.snoozeloo.ui.screens.detail

import com.devcampus.snoozeloo.core.UIEvent
import com.devcampus.snoozeloo.dto.AlarmEntity
import java.util.UUID
import java.util.UUID.randomUUID

sealed class AlarmDetailEvent : UIEvent {
    override val key: UUID = randomUUID()

    //    data class ChangeHourEvent(val hour: String) : AlarmDetailEvent()
    //    data class ChangeMinuteEvent(val minute: String) : AlarmDetailEvent()
    data class ChangeAlarmNameEvent(val name: String) : AlarmDetailEvent()
    data class DeleteAlarmEvent(val alarm: AlarmEntity) : AlarmDetailEvent()
    data class ChangeLabelDialogVisibilityEvent(val isVisible: Boolean) : AlarmDetailEvent()
    data class SaveAlarmEvent(val hour: Int, val minute: Int) : AlarmDetailEvent()
}