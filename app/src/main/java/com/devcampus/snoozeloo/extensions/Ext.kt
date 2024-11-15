package com.devcampus.snoozeloo.extensions

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.devcampus.snoozeloo.core.BaseViewModel
import com.devcampus.snoozeloo.core.UIEvent
import com.devcampus.snoozeloo.core.handleEvent

@Composable
fun <T> BaseViewModel<T>.HandleEvents(navController: NavController) {
    val eventsData by this.events.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = eventsData.key) {
        eventsData.events.forEach { event ->
            if (event !is UIEvent.CommonUiEvent.Unknown) {
                handleEvent(
                    composeViewModel = this@HandleEvents,
                    navController = navController,
                    event = event
                )
            }
            this@HandleEvents.removeEvent(event)
        }
    }
}
