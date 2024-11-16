package com.devcampus.snoozeloo.navigation

import com.devcampus.snoozeloo.dto.AlarmEntity
import kotlinx.serialization.Serializable

sealed interface Destinations {

    @Serializable
    object AlarmList : Destinations

    @Serializable
    object AlarmDetail : Destinations

    @Serializable
    data class AlarmTrigger(
        val alarm: AlarmEntity,
    ) : Destinations
}