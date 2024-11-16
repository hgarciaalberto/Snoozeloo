package com.devcampus.snoozeloo.ui.screens.trigger

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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.ColorFilter
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

    val state by viewModel.state.collectAsStateWithLifecycle()

    viewModel.run {
        HandleEvents(navController)
    }

    AlarmTriggerContent(
        alarm = alarm,
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
    )
}

@Composable
fun AlarmTriggerContent(
    alarm: AlarmEntity,
    modifier: Modifier = Modifier,
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
            onClick = {
                Timber.d("Turn Off Alarm")
            }
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