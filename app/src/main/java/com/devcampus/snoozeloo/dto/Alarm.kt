package com.devcampus.snoozeloo.dto

import kotlinx.serialization.Serializable

@Serializable
data class Alarm(
    val id: Int,
    val time: String,
    val repeat: String,
    val label: String,
    val enabled: Boolean
){
    constructor():this(
        id = 0,
        time = "",
        repeat = "",
        label = "",
        enabled = false
    )
}