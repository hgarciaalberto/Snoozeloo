package com.devcampus.snoozeloo.ui.screens.detail

import com.devcampus.snoozeloo.dto.AlarmEntity

data class AlarmDetailState(
    val alarm: AlarmEntity = AlarmEntity(),
    val isDialogVisible: Boolean = false,
)

//data class AlarmDetailState(
//    val alarmTime: AlarmTime = AlarmTime(),
//    val timeLeftUntilAlarm: AlarmTime? = AlarmTime(),
//    val alarmName: String = "Work",
//
//    )
//
//data class AlarmTime(
//    val hour: String = "12",
//    val minute: String = "45",
//)