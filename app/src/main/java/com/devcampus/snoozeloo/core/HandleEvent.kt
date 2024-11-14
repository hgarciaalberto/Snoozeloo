package com.devcampus.snoozeloo.core

import androidx.navigation.NavHostController

fun  <UiStateType> handleEvent(
    composeViewModel : BaseViewModel<UiStateType>,
    navController : NavHostController,
    event : UIEvent
) {
    when (event) {
        is UIEvent.CommonUiEvent.NavigationEvent.NavigateTo -> {
            navController.navigate(event.route)
        }

        else -> composeViewModel.handleEvent(event) // qui forse solo la callback del vm e non il vm

    }
}