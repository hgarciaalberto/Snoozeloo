package com.devcampus.snoozeloo.ui.screens.detail

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.devcampus.snoozeloo.R
import com.devcampus.snoozeloo.core.CommonUiEvent.NavigationEvent
import com.devcampus.snoozeloo.dto.AlarmEntity
import com.devcampus.snoozeloo.dto.timeFormat
import com.devcampus.snoozeloo.extensions.HandleEvents
import com.devcampus.snoozeloo.extensions.formatTimeUntil
import com.devcampus.snoozeloo.extensions.nextAlarmDate
import com.devcampus.snoozeloo.ui.theme.SnoozelooTheme
import com.devcampus.snoozeloo.ui.theme.fontStyle14Medium
import com.devcampus.snoozeloo.ui.theme.fontStyle16SemiBold
import com.devcampus.snoozeloo.ui.theme.fontStyle32Medium
import com.devcampus.snoozeloo.ui.theme.fontStyle52Medium
import java.util.Calendar

@Composable
fun AlarmDetailScreen(
    navController: NavController,
    alarm: AlarmEntity?,
    viewModel: AlarmDetailViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    val state by viewModel.state.collectAsStateWithLifecycle()

    viewModel.run {
        HandleEvents(navController)
    }

    LaunchedEffect(true) {
        viewModel.setupAlarm(alarm)
    }

    Scaffold { paddingValues ->
        AlarmDetailContent(
            hour = alarm?.getHour() ?: Calendar.getInstance().get(Calendar.HOUR_OF_DAY),
            minute = alarm?.getMinute() ?: Calendar.getInstance().get(Calendar.MINUTE),
            label = alarm?.label ?: "",
            isDeleteVisible = alarm != null,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
//            changeMinute = {
//                viewModel.handleEvent(AlarmDetailEvent.ChangeMinuteEvent(it))
//            },
//            changeHour = {
//                viewModel.handleEvent(AlarmDetailEvent.ChangeHourEvent(it))
//            },
            showDialog = {
                viewModel.handleEvent(AlarmDetailEvent.ChangeLabelDialogVisibilityEvent(true))
            },
            saveClicked = { hour, minute ->
                viewModel.emitEvent(AlarmDetailEvent.SaveAlarmEvent(context, hour, minute))
            },
            deleteClicked = {
                viewModel.emitEvent(AlarmDetailEvent.DeleteAlarmEvent(alarm!!))
            },
            closeClicked = {
                viewModel.emitEvent(NavigationEvent.NavigateBack)
            }
        )

        AnimatedVisibility(state.data!!.isDialogVisible) {
            DisplayDialog(
                label = state.data?.label ?: "",
                saveClicked = { name ->
                    viewModel.emitEvent(AlarmDetailEvent.ChangeAlarmNameEvent(name))
                },
                dismiss = {
                    viewModel.emitEvent(AlarmDetailEvent.ChangeLabelDialogVisibilityEvent(false))
                }
            )
        }
    }
}

@Composable
fun AlarmDetailContent(
    hour: Int,
    minute: Int,
    label: String,
    modifier: Modifier = Modifier,
    isDeleteVisible: Boolean = false,
    showDialog: () -> Unit = {},
    saveClicked: (Int, Int) -> Unit = { _, _ -> },
    deleteClicked: () -> Unit = { },
    closeClicked: () -> Unit = {},
) {

    var valueHour by rememberSaveable(hour) { mutableIntStateOf(hour) }
    var valueMinute by rememberSaveable(minute) { mutableIntStateOf(minute) }

    Column(
        modifier = modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        TopBarAlarmDetail(
            closeClicked = closeClicked,
            saveClicked = {
                saveClicked(valueHour, valueMinute)
            },
            deleteClicked = {
                deleteClicked()
            },
            isDeleteVisible = isDeleteVisible
        )

        Box(
            modifier = Modifier
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(10)
                )
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Row(modifier = Modifier) {
                    TextField(
                        modifier = Modifier // Add a white border
                            .padding(16.dp)
                            .weight(1f)
                            .size(width = 128.dp, height = 95.dp),
                        value = valueHour.coerceIn(0, 23).timeFormat(),
                        onValueChange = {
                            valueHour = it.toInt()
                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number
                        ),
                        colors = customColors(),
                        shape = RoundedCornerShape(10.dp),
                        textStyle = fontStyle52Medium.copy(textAlign = TextAlign.Center)
                    )
                    Text(
                        modifier = Modifier.align(Alignment.CenterVertically),
                        text = ":",
                        color = colorResource(id = R.color.moreGray),
                        style = fontStyle32Medium.copy(textAlign = TextAlign.Center)
                    )
                    TextField(
                        modifier = Modifier
                            .padding(16.dp)
                            .weight(1f)
                            .size(width = 128.dp, height = 95.dp), // Add padding
                        value = valueMinute.coerceIn(0, 59).timeFormat(),
                        onValueChange = {
                            valueMinute = it.toInt()
                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number
                        ),
                        colors = customColors(),
                        shape = RoundedCornerShape(10.dp),
                        textStyle = fontStyle52Medium.copy(textAlign = TextAlign.Center)
                    )
                }
                Text(
                    modifier = Modifier.padding(bottom = 16.dp),
                    style = fontStyle14Medium.copy(color = colorResource(id = R.color.moreGray)),
                    text = Calendar.getInstance().apply {
                        set(Calendar.HOUR_OF_DAY, valueHour)
                        set(Calendar.MINUTE, valueMinute)
                    }.time.nextAlarmDate(useWeekday = false).formatTimeUntil()
                )
            }
        }

        Box(
            modifier = Modifier
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(10)
                )
                .clickable {
                    showDialog()
                }
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = "Alarm name",
                    modifier = Modifier,
                    style = fontStyle16SemiBold.copy(color = colorResource(id = R.color.customBlack))
                )
                Text(
                    text = label,
                    modifier = Modifier,
                    style = fontStyle14Medium.copy(color = colorResource(id = R.color.moreGray))
                )

            }
        }
    }
}

@Composable
fun DisplayDialog(
    label: String,
    saveClicked: (String) -> Unit,
    dismiss: () -> Unit,
) {

    var selectedValue by rememberSaveable { mutableStateOf(label) }

    AlertDialog(
        onDismissRequest = dismiss,
        title = {
            Text(
                text = "Alarm name",
                style = fontStyle16SemiBold.copy(color = colorResource(id = R.color.customBlack))
            )
        },
        text = {
            OutlinedTextField(
                textStyle = fontStyle14Medium,
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = colorResource(id = R.color.moreGray),
                    focusedBorderColor = colorResource(id = R.color.moreGray)
                ),
                value = selectedValue,
                onValueChange = {
                    selectedValue = it
                })

        },
        confirmButton = {
            Button(onClick = {
                saveClicked(selectedValue)
            }) {
                Text("Save")
            }
        }
    )
}

@Composable
fun TopBarAlarmDetail(
    closeClicked: () -> Unit = {},
    saveClicked: () -> Unit = {},
    deleteClicked: () -> Unit = {},
    isDeleteVisible: Boolean = false,
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Icon(
            imageVector = Icons.Default.Close,
            contentDescription = "close",
            modifier = Modifier.clickable {
                closeClicked()
            }
        )
        Row {
            if (isDeleteVisible) {
                Button(
                    modifier = Modifier,
                    onClick = deleteClicked,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.error,
                    )
                ) {
                    Text(
                        text = "Delete",
                        style = fontStyle16SemiBold,
                        modifier = Modifier.width(IntrinsicSize.Max),
                    )
                }
            }

            Spacer(modifier = Modifier.width(12.dp))

            Button(
                modifier = Modifier,
                onClick = saveClicked

            ) {
                Text(
                    text = "Save",
                    style = fontStyle16SemiBold,
                    modifier = Modifier.width(IntrinsicSize.Max),
                )
            }
        }
    }
}

@Composable
fun customColors() = TextFieldDefaults.colors(
    unfocusedContainerColor = colorResource(id = R.color.background),
    focusedContainerColor = colorResource(id = R.color.background),
    unfocusedIndicatorColor = Color.Transparent,
    focusedIndicatorColor = Color.Transparent,
    focusedTextColor = colorResource(id = R.color.primary),
    unfocusedTextColor = colorResource(id = R.color.primary),
    cursorColor = colorResource(id = R.color.primary),
)

@Preview(showBackground = true)
@Composable
private fun AlarmDetailContentPreview(
    @PreviewParameter(AlarmDetailContentPreviewProvider::class) param: Boolean,
) {
    SnoozelooTheme {
        AlarmDetailContent(
            hour = 12,
            minute = 8,
            label = "Label",
            isDeleteVisible = param
        )
    }
}

class AlarmDetailContentPreviewProvider : PreviewParameterProvider<Boolean> {
    override val values: Sequence<Boolean> = sequenceOf(true, false)
}