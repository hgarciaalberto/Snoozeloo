package com.devcampus.snoozeloo.dto

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.util.Calendar
import java.util.Date


@Serializable
data class Alarm(
    val id: Int,
    val label: String,
    @Contextual val time: Date,
    val repeat: String,
    val enabled: Boolean
) {
    constructor() : this(
        id = -1,
        label = "",
        time = Calendar.getInstance().time,
        repeat = "",
        enabled = false
    )
}