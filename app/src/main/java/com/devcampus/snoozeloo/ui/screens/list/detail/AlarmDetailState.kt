package com.devcampus.snoozeloo.ui.screens.list.detail

data class AlarmDetailState (
    val alarmTime: AlarmTime = AlarmTime(),
    val timeLeftUntilAlarm : AlarmTime? = AlarmTime(),
    val alarmName: String = "Work"

)

data class AlarmTime(
    val hour : String = "12",
    val minute : String = "45"
)