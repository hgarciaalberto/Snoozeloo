package com.devcampus.snoozeloo.ui.screens.detail

import android.content.Context
import com.devcampus.snoozeloo.core.UIEvent
import com.devcampus.snoozeloo.dto.AlarmEntity
import java.util.UUID
import java.util.UUID.randomUUID

sealed class AlarmDetailEvent : UIEvent {
    override val key: UUID = randomUUID()

    data class ChangeAlarmNameEvent(val label: String) : AlarmDetailEvent()
    data class DeleteAlarmEvent(val alarm: AlarmEntity) : AlarmDetailEvent()
    data class ChangeLabelDialogVisibilityEvent(val isVisible: Boolean) : AlarmDetailEvent()
    data class SaveAlarmEvent(
        val context: Context,
        val alarm: AlarmEntity?,
        val hour: Int,
        val minute: Int,
    ) : AlarmDetailEvent()
}