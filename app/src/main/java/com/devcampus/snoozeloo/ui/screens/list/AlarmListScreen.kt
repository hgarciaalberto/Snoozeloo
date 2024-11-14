package com.devcampus.snoozeloo.ui.screens.list

import android.icu.text.SimpleDateFormat
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.devcampus.snoozeloo.R
import com.devcampus.snoozeloo.dto.Alarm
import com.devcampus.snoozeloo.navigation.Destinations
import com.devcampus.snoozeloo.ui.screens.list.AlarmListViewModel.Companion.FAKE_ALARMS
import com.devcampus.snoozeloo.ui.theme.SnoozelooTheme
import java.util.Locale

@Composable
fun AlarmListScreen(
    navController: NavController,
    viewModel: AlarmListViewModel = hiltViewModel()
) {

    val context = LocalContext.current
    val state by viewModel.state.collectAsStateWithLifecycle()

    Scaffold(
        floatingActionButton = {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(28.dp),
                contentAlignment = Alignment.BottomCenter
            ) {
                FloatingActionButton(
                    onClick = {
                        Toast.makeText(context, "TODO: Add Alarm", Toast.LENGTH_SHORT).show()
                        navController.navigate(Destinations.AlarmDetail)
                    },
                    containerColor = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .wrapContentSize()
                        .clip(MaterialTheme.shapes.extraLarge)
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        tint = MaterialTheme.colorScheme.onPrimary,
                        contentDescription = "Add Alarm",
                        modifier = Modifier.scale(1.5f, 1.5f)
                    )
                }
            }
        },
        modifier = Modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background,
    ) { paddingValues ->
        AlarmListContent(
            alarms = state.alarms,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            toggleAlarm = viewModel::toggleAlarm
        )
    }
}

@Composable
fun AlarmListContent(
    alarms: List<Alarm>,
    modifier: Modifier = Modifier,
    toggleAlarm: (Alarm) -> Unit = {},
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        Text(
            text = "Your Alarms",
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.padding(vertical = 8.dp)
        )

        LazyColumn(

        ) {
            items(alarms) { alarm ->

                ListItem(
                    modifier = Modifier
                        .padding(vertical = 8.dp)
                        .clip(MaterialTheme.shapes.large),
//                        .background(MaterialTheme.colorScheme.surface, shape = MaterialTheme.shapes.large),
                    overlineContent = {
                        Text(
                            text = alarm.label,
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.SemiBold
                            )
                        )
                    },
                    headlineContent = {
                        Row(
                            modifier = Modifier.padding(vertical = 8.dp),
                            verticalAlignment = Alignment.Bottom
                        ) {
                            Text(
                                text = SimpleDateFormat("HH:mm", Locale.getDefault()).format(alarm.time),
                                style = MaterialTheme.typography.displayMedium
                            )
                            Text(
                                text = SimpleDateFormat("a", Locale.getDefault()).format(alarm.time).uppercase(),
                                style = MaterialTheme.typography.headlineMedium,
                                modifier = Modifier.padding(4.dp)
                            )
                        }
                    },
                    supportingContent = {
                        Text(
                            text = "Supporting Content",
                            style = MaterialTheme.typography.bodySmall,
                            color = colorResource(id = R.color.disabled)
                        )
                    },
                    trailingContent = {
                        Switch(
                            checked = alarm.enabled,
                            onCheckedChange = {
                                toggleAlarm(alarm)
                            },
                            modifier = Modifier
                                .scale(1.3f, 1.2f)
                                .padding(end = 8.dp)
                        )
                    }
                )
            }
        }
    }
}

@Composable
@Preview(backgroundColor = 0xFFA8A5A5, showBackground = true)
private fun AlarmListContentPreview() {
    SnoozelooTheme {
        AlarmListContent(
            alarms = FAKE_ALARMS,
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
        )
    }
}