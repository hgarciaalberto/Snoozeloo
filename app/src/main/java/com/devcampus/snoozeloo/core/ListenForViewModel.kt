package com.devcampus.snoozeloo.core


import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController

@Composable
fun <T> ListenForViewModelState(
    viewModel: BaseViewModel<T>,
    content: @Composable BoxScope.(UiState<T>, Modifier) -> Unit,
){
    val state by viewModel.state.collectAsStateWithLifecycle()
    //da levare questa column e mettere un container apposito
    with(state) {
        Box {
            content.invoke(this, this@with, Modifier)
        }
    }
}

@Composable
fun <UiStateType> ListenForViewModelEvent(
    viewModel: BaseViewModel<UiStateType>,
    navController : NavHostController
) {
    val eventsData by viewModel.events.collectAsStateWithLifecycle()
    LaunchedEffect(key1 = eventsData.key) {
        eventsData.events.forEach { event ->

            if (event !is UIEvent.CommonUiEvent.Unknown) {
                handleEvent(
                    composeViewModel = viewModel,
                    navController = navController,
                    event = event
                )
            }
            viewModel.removeEvent(event)
        }
    }
}