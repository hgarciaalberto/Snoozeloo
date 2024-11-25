package com.devcampus.snoozeloo.ui.screens.trigger

import android.media.MediaPlayer
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.devcampus.snoozeloo.R
import com.devcampus.snoozeloo.dto.AlarmEntity
import com.devcampus.snoozeloo.extensions.HandleEvents
import com.devcampus.snoozeloo.ui.screens.trigger.AlarmTriggerViewModel.Companion.URI_RINGTONE
import com.devcampus.snoozeloo.ui.theme.SnoozelooTheme
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@Composable
fun AlarmTriggerScreen(
    navController: NavController,
    alarm: AlarmEntity,
    viewModel: AlarmTriggerViewModel = hiltViewModel(),
) {

    val context = LocalContext.current
    val state by viewModel.state.collectAsStateWithLifecycle()
    var mediaPlayer: MediaPlayer? by remember { mutableStateOf(null) }

    viewModel.run {
        HandleEvents(navController)
    }

    LaunchedEffect(state) {
        Timber.d("Alarm is on in LaunchedEffect: ${state.data?.isAlarmOn}")
        if (state.data?.isAlarmOn == true) {
            mediaPlayer = MediaPlayer.create(context, URI_RINGTONE).apply {
                isLooping = true // Set looping for continuous alarm sound
                start()
            }
        } else {
            mediaPlayer?.stop()
            mediaPlayer?.release()
            mediaPlayer = null
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            mediaPlayer?.stop()
            mediaPlayer?.release()
            mediaPlayer = null
        }
    }

    AlarmTriggerContent(
        alarm = alarm,
        modifier = Modifier.fillMaxSize(),
        turnOffClicked = {
            viewModel.handleEvent(TriggerAlarmEvents.SetupAlarm(false))
        }
    )
}

@Composable
fun AlarmTriggerContent(
    alarm: AlarmEntity,
    modifier: Modifier = Modifier,
    turnOffClicked: () -> Unit = {},
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(R.drawable.alarm),
            contentDescription = "Add Alarm",
            modifier = Modifier
                .scale(1.1f)
                .padding(12.dp),
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary)
        )

        Text(
            text = SimpleDateFormat("HH:mm", Locale.getDefault()).format(alarm.time),
            style = MaterialTheme.typography.displayLarge,
            color = MaterialTheme.colorScheme.primary,
            textAlign = TextAlign.Center,
            fontSize = 90.sp,
            modifier = Modifier
                .fillMaxWidth(0.7f)
                .padding(vertical = 8.dp)
        )

        Text(
            text = alarm.label,
            style = MaterialTheme.typography.headlineLarge,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth(0.7f)
                .padding(vertical = 8.dp)
        )

        Button(
            modifier = Modifier
                .fillMaxWidth(0.7f)
                .padding(vertical = 8.dp),
            onClick = turnOffClicked,
        ) {
            Text(
                text = "Turn Off",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.width(IntrinsicSize.Max),
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun AlarmTriggerContentPreview() {
    SnoozelooTheme {
        AlarmTriggerContent(
            modifier = Modifier.fillMaxSize(),
            alarm = AlarmEntity().copy(
                id = 2,
                label = "Education",
                time = Calendar.getInstance().apply {
                    set(Calendar.HOUR_OF_DAY, 2)
                    set(Calendar.MINUTE, 30)
                    set(Calendar.SECOND, 0)
                }.time,
                enabled = false,
            )
        )
    }
}