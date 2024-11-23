package com.devcampus.snoozeloo.ui.screens.detail

import com.devcampus.snoozeloo.dto.AlarmEntity

data class AlarmDetailState(
    val alarm: AlarmEntity?,
    val isDialogVisible: Boolean = false,
)
