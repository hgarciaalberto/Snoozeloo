package com.devcampus.snoozeloo.navigation

import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.devcampus.snoozeloo.dto.AlarmEntity
import com.devcampus.snoozeloo.ui.screens.detail.AlarmDetailScreen
import com.devcampus.snoozeloo.ui.screens.list.AlarmListScreen
import com.devcampus.snoozeloo.ui.screens.trigger.AlarmTriggerScreen
import com.kiwi.navigationcompose.typed.composable
import com.kiwi.navigationcompose.typed.createRoutePattern
import com.kiwi.navigationcompose.typed.navigate
import kotlinx.serialization.ExperimentalSerializationApi

@OptIn(ExperimentalSerializationApi::class)
@Composable
fun AppNavigation(pendingAlarm: AlarmEntity? = null) {

    val navController = rememberNavController()
    val alarmState = remember { mutableStateOf<AlarmEntity?>(pendingAlarm) }

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
                // Navigate to AlarmTriggerScreen if alarm is not null
                alarmState.value?.let {
                    navController.navigate(Destinations.AlarmTrigger(it))
                    alarmState.value = null
                }
            }

            composable<Destinations.AlarmDetail> {
                AlarmDetailScreen(
                    navController = navController,
                    alarm = alarm
                )
            }

            composable<Destinations.AlarmTrigger> {
                AlarmTriggerScreen(
                    alarm = alarm,
                    navController = navController,
                )
            }
        }

        // Navigate automatically if alarm is present
        LaunchedEffect(alarmState) {
            alarmState.value?.let {
                navController.navigate(Destinations.AlarmTrigger(it))
            }
        }
    }
}
