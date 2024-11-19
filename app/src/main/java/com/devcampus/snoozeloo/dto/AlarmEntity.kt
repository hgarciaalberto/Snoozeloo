package com.devcampus.snoozeloo.dto

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.util.Calendar
import java.util.Date

const val TABLE_NAME = "alarms"

@Parcelize
@Serializable
@Entity(tableName = TABLE_NAME)
data class AlarmEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val label: String,
    @Contextual val time: Date,
    val repeat: String,
    val enabled: Boolean,
) : Parcelable {
    constructor() : this(
        label = "",
        time = Calendar.getInstance().time,
        repeat = "",
        enabled = false
    )

    fun getHour(): Int {
        val calendar = Calendar.getInstance()
        calendar.time = time
        return calendar.get(Calendar.HOUR_OF_DAY)
    }

    fun getMinute(): Int {
        val calendar = Calendar.getInstance()
        calendar.time = time
        return calendar.get(Calendar.MINUTE)
    }
}
