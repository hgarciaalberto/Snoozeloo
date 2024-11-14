package com.devcampus.snoozeloo.core

import java.util.UUID

interface UIEvent {
    val key: UUID

    sealed class CommonUiEvent : UIEvent {
        override val key: UUID = UUID.randomUUID()

        sealed class NavigationEvent : CommonUiEvent() {
            data class NavigateTo(val route: String) : NavigationEvent()
        }

        data object Unknown : CommonUiEvent()


    }
}