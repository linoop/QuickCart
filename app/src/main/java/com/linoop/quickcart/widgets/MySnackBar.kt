package com.linoop.quickcart.widgets

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarData
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.linoop.quickcart.R
import com.linoop.quickcart.ui.theme.BlackShade
import com.linoop.quickcart.ui.theme.BrownShade
import com.linoop.quickcart.ui.theme.GrayShade
import com.linoop.quickcart.ui.theme.Teal
import com.linoop.quickcart.ui.theme.TealShade
import com.linoop.quickcart.utils.Constants.API_FAILED
import com.linoop.quickcart.utils.Constants.API_SUCCESS
import com.linoop.quickcart.utils.Constants.INFO

@Composable
fun MySnackBar(snackBarHostState: SnackbarHostState) {
    SnackbarHost(hostState = snackBarHostState, snackbar = {
        Snackbar(
            action = {
                IconButton(onClick = { it.dismiss() }) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = stringResource(id = R.string.close_button),
                        tint = if (isSystemInDarkTheme()) White else Black
                    )
                }
            },
            shape = RectangleShape,
            containerColor = if (isSystemInDarkTheme()) BrownShade else GrayShade
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                LeadingIcon(snackBarData = it)
                Text(
                    text = it.visuals.message,
                    color = if (isSystemInDarkTheme()) White else BlackShade,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodySmall,
                )
            }
        }
    })
}

@Composable
private fun LeadingIcon(snackBarData: SnackbarData) {
    when (snackBarData.visuals.actionLabel) {
        API_SUCCESS -> SuccessIcon()
        API_FAILED -> FailIcon()
        INFO -> InfoIcon()
    }
}

@Composable
fun InfoIcon() {
    Box(modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_medium))) {
        Icon(
            imageVector = Icons.Default.Info,
            contentDescription = null,
            tint = if (isSystemInDarkTheme()) TealShade else Teal,
            modifier = Modifier
                .padding(dimensionResource(id = R.dimen.padding_tiny))
                .align(Alignment.Center)
        )
    }
}

@Composable
private fun SuccessIcon() {
    Box(modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_medium))) {
        Icon(
            imageVector = Icons.Default.CheckCircle,
            contentDescription = null,
            tint = if (isSystemInDarkTheme()) TealShade else Teal,
            modifier = Modifier
                .padding(dimensionResource(id = R.dimen.padding_tiny))
                .align(Alignment.Center)
        )
    }
}

@Composable
private fun FailIcon() {
    Box(modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_medium))) {
        Icon(
            imageVector = Icons.Default.Warning,
            contentDescription = null,
            tint = if (isSystemInDarkTheme()) TealShade else Teal,
            modifier = Modifier
                .padding(dimensionResource(id = R.dimen.padding_tiny))
                .align(Alignment.Center)
        )
    }
}

