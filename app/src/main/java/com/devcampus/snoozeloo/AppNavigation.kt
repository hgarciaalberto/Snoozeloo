package com.devcampus.snoozeloo

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController

@Composable
fun AppNavigation() {

    val navController = rememberNavController()

    Scaffold { paddingValues ->
        Greeting(name = "Snoozeloo", modifier = Modifier.padding(paddingValues))
    }

}
