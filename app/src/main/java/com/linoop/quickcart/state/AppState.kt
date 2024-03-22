package com.linoop.quickcart.state

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.linoop.quickcart.utils.Constants.EMPTY
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class AppState(
    val navController: NavHostController,
    private val snackBarScope: CoroutineScope,
    val snackBarHostState: SnackbarHostState
) {
    fun showSnackBar(
        message: String = EMPTY,
        actionLabel: String = EMPTY,
        duration: SnackbarDuration = SnackbarDuration.Short
    ) {
        snackBarScope.launch {
            snackBarHostState.showSnackbar(
                message = message,
                actionLabel = actionLabel,
                duration = duration
            )
        }
    }
}

@Composable
fun rememberAppState(
    navController: NavHostController = rememberNavController(),
    snackBarScope: CoroutineScope = rememberCoroutineScope(),
    snackBarHostState: SnackbarHostState = remember { SnackbarHostState() }
) = remember(navController, snackBarScope, snackBarHostState) {
    AppState(
        navController = navController,
        snackBarScope = snackBarScope,
        snackBarHostState = snackBarHostState
    )
}