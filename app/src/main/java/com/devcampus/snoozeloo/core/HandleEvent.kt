package com.devcampus.snoozeloo.core

import androidx.navigation.NavController
import com.kiwi.navigationcompose.typed.navigate
import kotlinx.serialization.ExperimentalSerializationApi

@OptIn(ExperimentalSerializationApi::class)
fun <UiStateType> handleEvent(
    composeViewModel: BaseViewModel<UiStateType>,
    navController: NavController,
    event: UIEvent,
) {
    when (event) {
        is CommonUiEvent.NavigationEvent.NavigateTo -> {
            navController.navigate(event.route)
        }

        is CommonUiEvent.NavigationEvent.NavigateBack -> {
            navController.navigateUp()
        }

        else -> composeViewModel.handleEvent(event)
    }
}