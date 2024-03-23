package com.linoop.quickcart.utils

import androidx.compose.material3.SnackbarDuration


typealias ShowSnackBar = (message: String, actionLabel: String, duration: SnackbarDuration) -> Unit
typealias onValueChange = (value: String) -> Unit
typealias onTrailingIconClicked = () -> Unit
typealias onImeKeyAction = () -> Unit
typealias onClearIconClicked = () -> Unit
typealias onClick = () -> Unit
typealias onItemClick = (itemId: Int) -> Unit
typealias onSearch = (query: String) -> Unit