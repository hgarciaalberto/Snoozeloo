package com.devcampus.snoozeloo.ui.screens.list.detail

import com.devcampus.snoozeloo.core.BaseViewModel
import com.devcampus.snoozeloo.core.State
import com.devcampus.snoozeloo.core.UIEvent
import com.devcampus.snoozeloo.core.UiState
import com.devcampus.snoozeloo.dto.AlarmEntity
import com.devcampus.snoozeloo.ui.screens.list.AlarmListViewModel
import javax.inject.Inject

class AlarmDetailViewModel @Inject constructor() : BaseViewModel<AlarmDetailState>(
     defaultState = UiState (
        state = State.Loading(),
        data = AlarmDetailState()
    ))
 {

    override fun handleEvent(event: UIEvent) {
        when(event){
            is AlarmDetailEvent.ChangeAlarmNameEvent -> {
                emitStateCopy {
                    it?.copy(
                        alarmName = event.name
                    )
                }
            }
            is AlarmDetailEvent.ChangeMinuteEvent -> {
                emitStateCopy{
                    it?.copy(
                        alarmTime = it.alarmTime.copy(
                            minute = event.minute
                        )
                    )
                }

            }
            is AlarmDetailEvent.ChangeHourEvent -> {
                emitStateCopy{
                    it?.copy(
                        alarmTime = it.alarmTime.copy(
                            hour = event.hour
                        )
                    )
                }

            }
            else -> {
                super.handleEvent(event)
            }

        }
    }


}