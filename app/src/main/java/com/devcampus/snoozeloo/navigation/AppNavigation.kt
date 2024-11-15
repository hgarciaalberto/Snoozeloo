package com.devcampus.snoozeloo.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.devcampus.snoozeloo.core.ListenForViewModelEvent
import com.devcampus.snoozeloo.core.ListenForViewModelState
import com.devcampus.snoozeloo.core.UIEvent
import com.devcampus.snoozeloo.core.handleEvent
import com.devcampus.snoozeloo.ui.screens.list.AlarmListScreen
import com.devcampus.snoozeloo.ui.screens.list.AlarmListViewModel

@Composable
fun AppNavigation() {

    val navController = rememberNavController()

    Scaffold { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = Destinations.AlarmList,
            modifier = Modifier
                .statusBarsPadding()
                .consumeWindowInsets(paddingValues)
        ) {
            alarmListScreen(navController)

            composable<Destinations.AlarmDetail> {
//            val args = it.toRoute<Destinations.AlarmDetail>()
                AlarmDetailsScreen(
                    navController = navController,
//                alarm = args.alarm
                )
            }
        }
    }
}

fun NavGraphBuilder.alarmListScreen(navController: NavHostController) {
    composable<Destinations.AlarmList> {
        val viewModel = hiltViewModel<AlarmListViewModel>()
        ListenForViewModelState(
            viewModel = viewModel,
            content = { uiState, modifier ->
                uiState.data?.let {
                    AlarmListScreen(
                        modifier = modifier,
                        state = it,
                        handleEvent = { event : UIEvent -> handleEvent(
                            event = event,
                            navController = navController,
                            composeViewModel = viewModel
                        ) }
                    )
                }
            }
        )
        ListenForViewModelEvent(
            viewModel = viewModel,
            navController = navController
        )
    }
}

@Composable
fun AlarmDetailsScreen(navController: NavController) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Center) {
        Text(text = "Alarm Details")
    }
}
