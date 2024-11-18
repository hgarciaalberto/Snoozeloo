package com.devcampus.snoozeloo.ui.screens.list.detail

import com.devcampus.snoozeloo.core.UIEvent
import java.util.UUID
import java.util.UUID.randomUUID

sealed class AlarmDetailEvent : UIEvent {
    override val key: UUID = randomUUID()
    data class ChangeHourEvent(val hour : String) : AlarmDetailEvent()
    data class ChangeMinuteEvent(val minute : String) : AlarmDetailEvent()

}