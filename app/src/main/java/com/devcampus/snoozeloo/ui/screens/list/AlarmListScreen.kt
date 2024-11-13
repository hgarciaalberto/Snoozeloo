package com.devcampus.snoozeloo.ui.screens.list

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.devcampus.snoozeloo.dto.Alarm
import com.devcampus.snoozeloo.ui.theme.SnoozelooTheme

@Composable
fun AlarmListScreen(
    navController: NavController,
    viewModel: AlarmListViewModel = hiltViewModel()
) {

    val state = viewModel.state.value

    AlarmListContent(
        alarms = emptyList(),
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        toggleAlarm = viewModel::toggleAlarm
    )
}

@Composable
fun AlarmListContent(
    alarms: List<Alarm>,
    modifier: Modifier = Modifier,
    toggleAlarm: (Alarm) -> Unit = {},
) {
    Column(
        modifier = modifier.fillMaxSize(),
    ) {
        Text(
            text = "Your Alarms",
            style = MaterialTheme.typography.headlineLarge
        )

        Spacer(modifier = Modifier.padding(vertical = 8.dp))

        LazyColumn {
            items(alarms) { alarm ->

                ListItem(
                    headlineContent = {
                        Text(
                            text = "HeadLine Content",
                            style = MaterialTheme.typography.titleMedium
                        )
                    },
                    overlineContent = {
                        Text(
                            text = "Overline Content",
                            style = MaterialTheme.typography.bodySmall
                        )
                    },
                    supportingContent = {
                        Text(
                            text = "Supporting Content",
                            style = MaterialTheme.typography.bodySmall
                        )
                    },
                    trailingContent = {
                        Switch(
                            checked = alarm.enabled,
                            colors = SwitchDefaults.colors().copy(
                                checkedThumbColor = MaterialTheme.colorScheme.primary,
                            ),
                            onCheckedChange = {
                                toggleAlarm(alarm)
                            }
                        )
                    }
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun AlarmListContentPreview() {
    SnoozelooTheme {
        AlarmListContent(
            alarms = listOf(
                Alarm().copy(
                    time = "07:00",
                    enabled = true,
                ),
                Alarm().copy(
                    time = "08:00",
                    enabled = true,
                ),
                Alarm().copy(
                    time = "09:00",
                    enabled = false,
                ),
                Alarm().copy(
                    time = "10:00",
                    enabled = false,
                ),
            )
        )
    }
}