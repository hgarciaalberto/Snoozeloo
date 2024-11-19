package com.devcampus.snoozeloo.navigation

import android.os.Parcelable
import com.devcampus.snoozeloo.dto.AlarmEntity
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

sealed interface Destinations {

    @Serializable
    object AlarmList : Destinations

    @Parcelize
    @Serializable
    data class AlarmDetail(
        val alarm: AlarmEntity = AlarmEntity(),
    ) : Parcelable, Destinations
}
