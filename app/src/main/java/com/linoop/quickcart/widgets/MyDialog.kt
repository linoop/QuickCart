package com.linoop.acronymapp.widgets

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import com.linoop.quickcart.R

@Composable
fun DrawAlertDialog(
    title: String,
    message: String,
    imageVector: ImageVector = Icons.Default.Info,
    imageDescription: String = stringResource(id = R.string.information_icon),
    confirmText: String = stringResource(id = R.string.ok),
    dismissText: String? = null,
    onDismissRequest: () -> Unit = {},
    onConfirmation: () -> Unit = {},
) {
    AlertDialog(
        icon = { Icon(imageVector, contentDescription = imageDescription) },
        title = { Text(text = title) },
        text = { Text(text = message) },
        onDismissRequest = { onDismissRequest() },
        confirmButton = { TextButton(onClick = { onConfirmation() }) { Text(text = confirmText) } },
        dismissButton = {
            if (dismissText != null) TextButton(onClick = { onDismissRequest() }) { Text(text = dismissText.toString()) }
        }
    )
}
