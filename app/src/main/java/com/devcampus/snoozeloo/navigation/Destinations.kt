package com.devcampus.snoozeloo.navigation

import com.devcampus.snoozeloo.dto.Alarm
import kotlinx.serialization.Serializable

object Destinations {

    @Serializable
    object AlarmList

    @Serializable
    data class AlarmDetail(val alarm: Alarm)
}