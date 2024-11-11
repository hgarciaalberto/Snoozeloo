package com.devcampus.snoozeloo.navigation

import kotlinx.serialization.Serializable

object Destinations {

    @Serializable
    data class Greetings(val name: String = "Greeting")
}