package com.devcampus.snoozeloo

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.devcampus.snoozeloo.dto.AlarmEntity
import com.devcampus.snoozeloo.navigation.AppNavigation
import com.devcampus.snoozeloo.ui.theme.SnoozelooTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        setContent {
            // Disable dark theme
            SnoozelooTheme(darkTheme = false, dynamicColor = false) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.primary,
                ) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        AppNavigation(intent.extras?.getParcelable("alarm", AlarmEntity::class.java))
                    } else {
                        @Suppress("DEPRECATION")
                        AppNavigation(intent.extras?.getParcelable<AlarmEntity>("alarm"))
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SnoozelooTheme {
        AppNavigation()
    }
}