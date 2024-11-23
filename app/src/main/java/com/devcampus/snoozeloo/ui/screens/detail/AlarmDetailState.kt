package com.devcampus.snoozeloo.ui.screens.detail

import com.devcampus.snoozeloo.dto.AlarmEntity

data class AlarmDetailState(
    val alarm: AlarmEntity = AlarmEntity(),
    val isDialogVisible: Boolean = false,
)
