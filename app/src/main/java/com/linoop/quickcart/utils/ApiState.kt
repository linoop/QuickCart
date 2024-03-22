package com.linoop.quickcart.utils

import androidx.compose.runtime.Composable
import com.linoop.quickcart.utils.Constants.EMPTY

open class BaseState(
    var message: String = EMPTY,
    var errorCode: String? = null
)


enum class ApiState {
    Initial,
    Loading,
    Success,
    Error;

    @Composable
    fun ResolveState(
        initial: @Composable () -> Unit = {},
        loading: @Composable () -> Unit = {},
        success: @Composable () -> Unit = {},
        error: @Composable () -> Unit = {},
    ) {
        when (this) {
            Initial -> initial.invoke()
            Loading -> loading.invoke()
            Success -> success.invoke()
            Error -> error.invoke()
        }
    }
}