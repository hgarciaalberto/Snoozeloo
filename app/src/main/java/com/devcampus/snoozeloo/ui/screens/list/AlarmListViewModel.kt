package com.devcampus.snoozeloo.ui.screens.list

import android.annotation.SuppressLint
import android.icu.text.SimpleDateFormat
import com.devcampus.snoozeloo.core.BaseViewModel
import com.devcampus.snoozeloo.dto.Alarm
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class AlarmListViewModel @Inject constructor() : BaseViewModel<AlarmListViewModel.AlarmListState>() {

//    val state: StateFlow<AlarmListState>
//        field : MutableStateFlow<State> = MutableStateFlow(AlarmListState())

    init {
        launch {
            emitLoadingSuspend()
            emitSuccessSuspend(AlarmListState(FAKE_ALARMS))
        }
    }

    fun toggleAlarm(alarm: Alarm) = launch {
        emitSuccessSuspend(
            // I rather not to create this AlarmListState object here.
            AlarmListState(
                state.value.data!!.alarms.map {
                    if (it.id == alarm.id) {
                        it.copy(enabled = !it.enabled)
                    } else {
                        it
                    }
                }
            )
        )
    }

    data class AlarmListState(
        val alarms: List<Alarm> = emptyList<Alarm>()
    )

    companion object {
        @SuppressLint("ConstantLocale")
        val FAKE_ALARMS = listOf(
            Alarm().copy(
                id = 0,
                label = "Wake up",
                time = SimpleDateFormat("hh:mm a", Locale.getDefault()).parse("07:00 AM")!!,
                enabled = true,
            ),
            Alarm().copy(
                id = 1,
                label = "Go to bed",
                time = SimpleDateFormat("hh:mm a", Locale.getDefault()).parse("08:00 PM")!!,
                enabled = true,
            ),
            Alarm().copy(
                id = 2,
                label = "Education",
                time = SimpleDateFormat("hh:mm a", Locale.getDefault()).parse("09:00 AM")!!,
                enabled = false,
            ),
            Alarm().copy(
                id = 3,
                label = "Dinner",
                time = SimpleDateFormat("hh:mm a", Locale.getDefault()).parse("10:00 PM")!!,
                enabled = false,
            ),
            Alarm().copy(
                id = 4,
                label = "Education",
                time = SimpleDateFormat("hh:mm a", Locale.getDefault()).parse("09:00 AM")!!,
                enabled = false,
            ),
            Alarm().copy(
                id = 5,
                label = "Dinner",
                time = SimpleDateFormat("hh:mm a", Locale.getDefault()).parse("10:00 PM")!!,
                enabled = false,
            ),
            Alarm().copy(
                id = 6,
                label = "Education",
                time = SimpleDateFormat("hh:mm a", Locale.getDefault()).parse("09:00 AM")!!,
                enabled = false,
            ),
            Alarm().copy(
                id = 7,
                label = "Dinner",
                time = SimpleDateFormat("hh:mm a", Locale.getDefault()).parse("10:00 PM")!!,
                enabled = false,
            ),
        )
    }
}
