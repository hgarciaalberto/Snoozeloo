package com.devcampus.snoozeloo.ui.screens.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devcampus.snoozeloo.dto.Alarm
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
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
        private val FAKE_ALARMS = listOf(
            Alarm(1, "8:00", "Everyday", "Wake up", true),
            Alarm(2, "9:00", "Weekdays", "Work", true),
            Alarm(3, "10:00", "Weekends", "Sleep in", true),
        )
    }

}
