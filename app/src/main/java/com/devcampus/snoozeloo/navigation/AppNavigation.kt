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
import androidx.navigation.NavController
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
//            val args = it.toRoute<Destinations.AlarmDetail>()
                AlarmDetailsScreen(
                    navController = navController,
//                alarm = args.alarm
                )
            }
        }
    }
}

@Composable
fun AlarmDetailsScreen(navController: NavController) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Center) {
        Text(text = "Alarm Details")
    }
}
