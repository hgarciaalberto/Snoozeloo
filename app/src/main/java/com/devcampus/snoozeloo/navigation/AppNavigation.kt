package com.devcampus.snoozeloo.navigation

import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.devcampus.snoozeloo.ui.screens.detail.AlarmDetailScreen
import com.devcampus.snoozeloo.ui.screens.list.AlarmListScreen
import com.kiwi.navigationcompose.typed.composable
import com.kiwi.navigationcompose.typed.createRoutePattern
import kotlinx.serialization.ExperimentalSerializationApi

@OptIn(ExperimentalSerializationApi::class)
@Composable
fun AppNavigation() {

    val navController = rememberNavController()

    Scaffold { paddingValues ->

        NavHost(
            navController = navController,
            startDestination = createRoutePattern<Destinations.AlarmList>(),
            modifier = Modifier
                .statusBarsPadding()
                .consumeWindowInsets(paddingValues)
        ) {
            composable<Destinations.AlarmList> {
                AlarmListScreen(
                    navController = navController,
                )
            }

            composable<Destinations.AlarmDetail> {
                AlarmDetailScreen(
                    navController = navController,
                    alarm = alarm
                )
            }
        }
    }
}
