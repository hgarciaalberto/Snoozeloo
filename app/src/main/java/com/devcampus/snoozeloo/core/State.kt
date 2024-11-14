package com.devcampus.snoozeloo.core

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.UUID

/**
 * This class represents the "state" of the state
 */
@Parcelize
sealed class State : Parcelable {
    abstract val id : String

    data class Loading(val loadingType : LoadingType = LoadingType.DEFAULT_CIRCULAR) : State() {
        override val id: String
            get() = UUID.randomUUID().toString()
    }

    data object Success : State() {
        override val id: String
            get() = UUID.randomUUID().toString()
    }

    // Instead of a message, a data model can be used for an error dialog
    data class Error(val message : String) : State() {
        override val id: String
            get() = UUID.randomUUID().toString()
    }
}