package com.devcampus.snoozeloo.ui.screens.list.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
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
import com.devcampus.snoozeloo.ui.theme.fontStyle14Medium
import com.devcampus.snoozeloo.ui.theme.fontStyle16SemiBold
import com.devcampus.snoozeloo.ui.theme.fontStyle32Medium
import com.devcampus.snoozeloo.ui.theme.fontStyle52Medium
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@Composable
fun AlarmDetailScreen(
    navController: NavController,
    viewModel: AlarmDetailViewModel = hiltViewModel(),
) {

    val state by viewModel.state.collectAsStateWithLifecycle()

    viewModel.run {
        HandleEvents(navController)
    }

    Scaffold() { paddingValues ->
        AlarmDetailContent(
            viewModel = viewModel,
            state = state.data?: AlarmDetailState(),
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
        )
    }


}

@Composable
fun AlarmDetailContent(
    state: AlarmDetailState,
    viewModel: AlarmDetailViewModel,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TopBarAlarmDetail()
        Box(
            modifier = Modifier
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(10)
                )
        ){
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Row(modifier = Modifier) {
                    TextField(
                        modifier = Modifier// Add a white border
                            .padding(16.dp)
                            .weight(1f)
                            .size(width = 128.dp, height = 95.dp), // Add padding TODO como se puede mejorar esto?
                        value = state.alarmTime.hour,
                        onValueChange = {
                            viewModel.emitEvent(AlarmDetailEvent.ChangeHourEvent(it))
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
                        value = state.alarmTime.minute.toString(),
                        onValueChange = {
                            viewModel.emitEvent(AlarmDetailEvent.ChangeMinuteEvent(it))
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
                    text = "Alarm in 7h 34min"
                )
            }
           
        }

        Box(
            modifier = Modifier
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(10)
                )
        ){
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth().padding(16.dp)
            ){
                Text(
                    modifier = Modifier,
                    text = "Alarm name",
                    style = fontStyle16SemiBold.copy(color = colorResource(id = R.color.customBlack))
                )
                Text(
                    modifier = Modifier,
                    text = state.alarmName,
                    style = fontStyle14Medium.copy(color = colorResource(id = R.color.moreGray))
                )

            }
        }
    }
}

@Composable
fun TopBarAlarmDetail(){
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Icon(
            painter = painterResource(
                id = (R.drawable.close_icon)
            ),
            contentDescription = "close"
        )
        Button(
            modifier = Modifier,
            onClick = {
                Timber.d("Turn Off Alarm")
            }
        ) {
            Text(
                text = "Save",
                style = fontStyle16SemiBold,
                modifier = Modifier.width(IntrinsicSize.Max),
            )
        }
    }
}

@Composable
fun customColors() =
    TextFieldDefaults.colors(
        unfocusedContainerColor = colorResource(id = R.color.background),
        focusedContainerColor = colorResource(id = R.color.background),
        unfocusedIndicatorColor = Color.Transparent,
        focusedIndicatorColor = Color.Transparent,
        focusedTextColor = colorResource(id = R.color.primary),
        unfocusedTextColor = colorResource(id = R.color.primary),
        cursorColor = colorResource(id = R.color.primary),
    )

