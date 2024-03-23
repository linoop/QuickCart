package com.linoop.quickcart.cart.presentation

import androidx.compose.material3.SnackbarDuration
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.linoop.quickcart.R
import com.linoop.quickcart.cart.state.CartPageUserState
import com.linoop.quickcart.utils.ApiState
import com.linoop.quickcart.utils.Constants
import com.linoop.quickcart.utils.ShowSnackBar
import com.linoop.quickcart.widgets.ProgressDialog

@Composable
fun CartPageViewState(
    navController: NavController,
    showSnackBar: ShowSnackBar,
    state: CartPageUserState,
    drawContent: @Composable () -> Unit
) {
    state.openCartDataState.value.apiState.ResolveState(
        loading = { ProgressDialog {} },
        success = { drawContent.invoke() },
        error = {
            showSnackBar(
                stringResource(id = R.string.no_record_found),
                Constants.API_FAILED,
                SnackbarDuration.Short
            )
            state.openCartDataState.value.apiState = ApiState.Initial
        }
    )
    state.deleteItemDataState.value.apiState.ResolveState(
        loading = { ProgressDialog {} },
        success = {  },
        error = {
            showSnackBar(
                stringResource(id = R.string.database_error),
                Constants.API_FAILED,
                SnackbarDuration.Short
            )
            state.openCartDataState.value.apiState = ApiState.Initial
        }
    )
}