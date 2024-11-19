package com.devcampus.snoozeloo.core

import androidx.navigation.NavController

fun  <UiStateType> handleEvent(
    composeViewModel : BaseViewModel<UiStateType>,
    navController : NavController,
    event : UIEvent
) {
    when (event) {
        is CommonUiEvent.NavigationEvent.NavigateTo -> {
            navController.navigate(event.route)
        }

        else -> composeViewModel.handleEvent(event)
    }
}