package com.devcampus.snoozeloo.core

import java.util.UUID

data class EventsData(
    val key : UUID = UUID.randomUUID(),
    val events: List<UIEvent> = listOf(UIEvent.CommonUiEvent.Unknown)
)