package com.linoop.quickcart.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.linoop.quickcart.ui.theme.Black
import com.linoop.quickcart.ui.theme.LightGray

@Composable
fun ProgressDialog(onDismiss: () -> Unit) {
    Dialog(
        onDismissRequest = { onDismiss() },
        properties = DialogProperties(
            dismissOnBackPress = false,
            dismissOnClickOutside = false,
            usePlatformDefaultWidth = false
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Black.copy(alpha = 0.05f)),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(color = LightGray)
        }
    }
}
