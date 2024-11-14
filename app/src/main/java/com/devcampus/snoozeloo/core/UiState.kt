package com.devcampus.snoozeloo.core

import java.util.UUID

data class UiState<T>(
    val id : UUID = UUID.randomUUID(),
    val data : T?,
    override val state: State
) : StateInterface {

    override fun copyState(state: State): StateInterface = this.copy(state = state)

}