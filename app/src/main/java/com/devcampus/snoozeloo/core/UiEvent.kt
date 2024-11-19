package com.devcampus.snoozeloo.core

import com.devcampus.snoozeloo.navigation.Destinations
import java.util.UUID

interface UIEvent {
    val key: UUID
}

sealed class CommonUiEvent : UIEvent {
    override val key: UUID = UUID.randomUUID()

    sealed class NavigationEvent : CommonUiEvent() {
        data class NavigateTo(val route: Destinations) : NavigationEvent()
        object NavigateBack : NavigationEvent()
    }

    data object Unknown : CommonUiEvent()
}
