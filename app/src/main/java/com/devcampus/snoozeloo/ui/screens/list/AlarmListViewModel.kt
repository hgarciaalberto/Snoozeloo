package com.devcampus.snoozeloo.ui.screens.list

import android.annotation.SuppressLint
import android.icu.text.SimpleDateFormat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devcampus.snoozeloo.dto.Alarm
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class AlarmListViewModel @Inject constructor() : ViewModel() {

    val state: StateFlow<State>
        field : MutableStateFlow<State> = MutableStateFlow(State())

    init {
        viewModelScope.launch {
            state.update {
                it.copy(
                    alarms = FAKE_ALARMS,
                    isLoading = false,
                )
            }
        }
    }

    fun toggleAlarm(alarm: Alarm) {
        state.update {
            it.copy(
                alarms = it.alarms.map {
                    if (it.id == alarm.id) {
                        it.copy(enabled = !it.enabled)
                    } else {
                        it
                    }
                }
            )
        }
    }

    data class State(
        val alarms: List<Alarm>,
        val isLoading: Boolean,
    ) {
        constructor() : this(
            alarms = emptyList(),
            isLoading = false,
        )
    }

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
