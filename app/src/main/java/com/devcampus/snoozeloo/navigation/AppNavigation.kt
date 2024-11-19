package com.devcampus.snoozeloo.navigation

import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.devcampus.snoozeloo.ui.screens.list.AlarmListScreen
import com.devcampus.snoozeloo.ui.screens.detail.AlarmDetailScreen

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
            composable<Destinations.AlarmList> {
                AlarmListScreen(
                    navController = navController,
                )
            }

            composable<Destinations.AlarmDetail> {
                val args = it.toRoute<Destinations.AlarmDetail>()
                AlarmDetailScreen(
                    navController = navController,
                    alarm= args.alarm
                )
            }
        }
    }
}
