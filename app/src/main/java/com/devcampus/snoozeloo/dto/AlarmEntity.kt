package com.devcampus.snoozeloo.dto

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

const val TABLE_NAME = "alarms"

@Serializable
@Entity(tableName = TABLE_NAME)
data class AlarmEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val label: String,
    @Serializable(with = DateSerializer::class)
    val time: Date,
    val repeat: String,
    val enabled: Boolean,
) {

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

fun Int.timeFormat(): String =
    String.format(Locale.getDefault(), "%02d", this)


object DateSerializer : KSerializer<Date> {
    private val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ", Locale.getDefault())

    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("Date", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: Date) {
        val string = dateFormat.format(value)
        encoder.encodeString(string)
    }

    override fun deserialize(decoder: Decoder): Date {
        val string = decoder.decodeString()
        return dateFormat.parse(string)!!
    }
}