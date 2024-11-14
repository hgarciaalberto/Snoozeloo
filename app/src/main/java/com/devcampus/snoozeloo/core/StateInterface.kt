package com.devcampus.snoozeloo.core

interface StateInterface {
    val state : State

    fun copyState(state : State) : StateInterface
}