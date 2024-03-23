package com.linoop.quickcart.product.presentation

import androidx.compose.material3.SnackbarDuration
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.linoop.acronymapp.widgets.DrawAlertDialog
import com.linoop.quickcart.R
import com.linoop.quickcart.product.state.ProductPageUserState
import com.linoop.quickcart.utils.ApiState
import com.linoop.quickcart.utils.Constants.API_FAILED
import com.linoop.quickcart.utils.Constants.API_SUCCESS
import com.linoop.quickcart.utils.ShowSnackBar
import com.linoop.quickcart.widgets.ProgressDialog

@Composable
fun ProductPageViewState(
    showSnackBar: ShowSnackBar,
    state: ProductPageUserState
) {
    val signupDataState = state.dataState.value
    signupDataState.apiState.ResolveState(
        loading = { ProgressDialog {} },
        success = {
            showSnackBar(signupDataState.message, API_SUCCESS, SnackbarDuration.Short)
            state.dataState.value.apiState = ApiState.Initial
        },
        error = {
            showSnackBar(signupDataState.message, API_FAILED, SnackbarDuration.Short)
            state.dataState.value.apiState = ApiState.Initial
        }
    )
    if (state.showInfo.value) DrawAlertDialog(
        title = stringResource(id = R.string.app_name),
        message = stringResource(id = R.string.app_desc),
        onConfirmation = { state.showInfo.value = false }
    )
}