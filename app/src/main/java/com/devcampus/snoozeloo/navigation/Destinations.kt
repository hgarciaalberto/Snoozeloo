package com.devcampus.snoozeloo.navigation

import kotlinx.serialization.Serializable

sealed interface Destinations {

    @Serializable
    object AlarmList : Destinations

    @Serializable
    object AlarmDetail : Destinations
}