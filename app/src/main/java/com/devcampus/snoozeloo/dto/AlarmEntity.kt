package com.devcampus.snoozeloo.dto

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.util.Calendar
import java.util.Date

const val TABLE_NAME = "alarms"

@Serializable
@Entity(tableName = TABLE_NAME)
data class AlarmEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val label: String,
    @Contextual val time: Date,
    val repeat: String,
    val enabled: Boolean
) {
    constructor() : this(
        label = "",
        time = Calendar.getInstance().time,
        repeat = "",
        enabled = false
    )
}
