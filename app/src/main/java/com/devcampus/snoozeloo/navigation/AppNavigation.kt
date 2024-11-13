package com.devcampus.snoozeloo.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.devcampus.snoozeloo.ui.screens.list.AlarmListScreen

@Composable
fun AppNavigation() {

    val navController = rememberNavController()

    Scaffold { paddingValues ->

        NavHost(
            navController = navController,
            startDestination = Destinations.AlarmList,
            modifier = Modifier.padding(paddingValues),
        ) {
            composable<Destinations.AlarmList> {
                AlarmListScreen(
                    navController = navController,
                )
            }
        }
    }
}
