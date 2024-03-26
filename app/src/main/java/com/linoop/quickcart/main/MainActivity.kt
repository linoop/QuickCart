package com.linoop.quickcart.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.linoop.quickcart.main.state.rememberAppState
import com.linoop.quickcart.widgets.MySnackBar
import com.linoop.quickcart.navigation.ScreenNav
import com.linoop.quickcart.ui.theme.QuickCartTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val appState = rememberAppState()
            QuickCartTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Scaffold(
                        snackbarHost = { MySnackBar(snackBarHostState = appState.snackBarHostState) }
                    ) { padding ->
                        Column(modifier = Modifier.padding(padding)) {
                            ScreenNav(
                                navHostController = appState.navController,
                                showSnackBar = { message, actionLabel, duration ->
                                    appState.showSnackBar(
                                        message,
                                        actionLabel,
                                        duration
                                    )
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}