package com.devcampus.snoozeloo.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.devcampus.snoozeloo.Greeting

@Composable
fun AppNavigation() {

    val navController = rememberNavController()

    Scaffold { paddingValues ->

        NavHost(
            navController = navController,
            startDestination = Destinations.Greetings,
            modifier = Modifier.padding(paddingValues),
        ) {
            composable<Destinations.Greetings> {
                val args = it.toRoute<Destinations.Greetings>()
                Greeting(args.name)
            }
        }
    }
}
