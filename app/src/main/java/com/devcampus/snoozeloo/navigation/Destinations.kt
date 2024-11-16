package com.devcampus.snoozeloo.navigation

import com.devcampus.snoozeloo.dto.AlarmEntity
import com.kiwi.navigationcompose.typed.Destination
import kotlinx.serialization.Serializable

sealed interface Destinations : Destination {

    @Serializable
    object AlarmList : Destinations

    @Serializable
    data class AlarmDetail(
        val alarm: AlarmEntity?,
    ) : Destinations

    @Serializable
    data class AlarmTrigger(
        val alarm: AlarmEntity,
    ) : Destinations
}
