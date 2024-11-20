package com.devcampus.snoozeloo.ui.screens.detail

import com.devcampus.snoozeloo.core.BaseViewModel
import com.devcampus.snoozeloo.core.State
import com.devcampus.snoozeloo.core.UIEvent
import com.devcampus.snoozeloo.core.UiState
import com.devcampus.snoozeloo.dto.AlarmEntity
import java.util.Calendar
import javax.inject.Inject

class AlarmDetailViewModel @Inject constructor() : BaseViewModel<AlarmDetailState>(
    defaultState = UiState(
        state = State.Loading(),
        data = AlarmDetailState()
    )
) {

    override fun handleEvent(event: UIEvent) {
        when (event) {
            is AlarmDetailEvent.ChangeAlarmNameEvent -> {
                emitStateCopy {
                    it?.copy(
                        alarm = it.alarm.copy(
                            label = event.name
                        )
                    )
                }
            }

//            is AlarmDetailEvent.ChangeMinuteEvent -> {
//                emitStateCopy {
//                    it?.copy(
//                        alarm = it.alarm.copy(
//                            time = Calendar.getInstance().apply {
//                                time = it.alarm.time
//                                set(Calendar.MINUTE, event.minute.toInt())
//                            }.time
//                        )
//                    )
//                }
//            }
//
//            is AlarmDetailEvent.ChangeHourEvent -> {
//                emitStateCopy {
//                    it?.copy(
//                        alarm = it.alarm.copy(
//                            time = Calendar.getInstance().apply {
//                                time = it.alarm.time
//                                set(Calendar.MINUTE, event.hour.toInt())
//                            }.time
//                        )
//                    )
//                }
//            }

            is AlarmDetailEvent.SaveAlarmEvent -> {
                emitStateCopy {
                    it?.copy(
                        alarm = it.alarm.copy(
                            time = Calendar.getInstance().apply {
                                time = it.alarm.time
                                set(Calendar.HOUR_OF_DAY, event.hour.toInt())
                                set(Calendar.MINUTE, event.minute.toInt())
                            }.time
                        )
                    )
                }
            }

            is AlarmDetailEvent.ChangeLabelDialogVisibilityEvent -> {
                emitStateCopy {
                    it?.copy(
                        isDialogVisible = event.isVisible
                    )
                }
            }

            else -> {
                super.handleEvent(event)
            }
        }
    }

    fun setupAlarm(alarm: AlarmEntity?) {
        emitStateCopy {
            it?.copy(
                alarm = alarm ?: AlarmEntity()
            )
        }
    }
}