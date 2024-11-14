package com.devcampus.snoozeloo.core

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.UUID

abstract class BaseViewModel<T>(
    defaultState: UiState<T> = UiState(state = State.Loading(), data = null),
    val coroutineDispatcher: CoroutineDispatcher? = Dispatchers.IO
) : ViewModel() {

    val state: StateFlow<UiState<T>>
        field:MutableStateFlow<UiState<T>> = MutableStateFlow(defaultState)

    val events: StateFlow<EventsData>
        field:MutableStateFlow<EventsData> = MutableStateFlow(EventsData())

    fun launch(block: suspend CoroutineScope.() -> Unit) =
        viewModelScope.launch(coroutineDispatcher ?: Dispatchers.IO, block = block)

    suspend fun emitLoadingSuspend() =
        state.emit(
            state.value.copy(
                state = State.Loading(), id = UUID.randomUUID()
            )
        )

    fun emitLoading() = launch { emitLoadingSuspend() }

    suspend fun emitSuccessSuspend() =
        state.emit(state.value.copy(state = State.Success, id = UUID.randomUUID()))

    fun emitSuccess() = launch { emitSuccessSuspend() }

    suspend fun emitSuccessSuspend(model: T) = emitStateSuspend(
        UiState(
            data = model,
            state = State.Success
        )
    )

    fun emitState(
        state: UiState<T>
    ) = launch { emitStateSuspend(state) }

    suspend fun emitStateSuspend(
        state: UiState<T>
    ) = this.state.emit(state)

    suspend fun emitStateCopySuspend(
        newState: State = State.Success,
        data: (T?) -> T? = { state.value.data }
    ) = state.emit(
        UiState(
            state = newState,
            data = data.invoke(state.value.data)
        )
    )

    fun emitStateCopy(
        newState: State = State.Success,
        data: (T?) -> T? = { null }
    ) = launch { emitStateCopySuspend(newState, data) }


    fun emitEvent(vararg event: UIEvent) = launch { emitEventSuspend(*event) }

    fun emitEventSuspend(vararg event: UIEvent) {
        events.update {
            it.copy(
                events = it.events.plus(event.toList()),
                key = UUID.randomUUID()
            )
        }
    }

    fun removeEvent(event: UIEvent) {
        events.update { composeEvents ->
            composeEvents.copy(
                key = if (composeEvents.events.size == 1) UUID.randomUUID() else composeEvents.key,
                events = composeEvents.events.filter { event.key != it.key }
            )
        }
    }

    open fun handleEvent(event: UIEvent) {
        Timber.tag("BaseViewModel").d("event unhandled")
    }
}