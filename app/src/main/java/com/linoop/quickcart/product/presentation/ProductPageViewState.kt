package com.linoop.quickcart.product.presentation

import androidx.compose.material3.SnackbarDuration
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.linoop.quickcart.widgets.DrawAlertDialog
import com.linoop.quickcart.R
import com.linoop.quickcart.navigation.Screen
import com.linoop.quickcart.product.state.ProductPageUserState
import com.linoop.quickcart.utils.ApiState
import com.linoop.quickcart.utils.Constants.API_FAILED
import com.linoop.quickcart.utils.Constants.API_SUCCESS
import com.linoop.quickcart.utils.ShowSnackBar
import com.linoop.quickcart.widgets.ProgressDialog

@Composable
fun ProductPageViewState(
    navController: NavController,
    showSnackBar: ShowSnackBar,
    state: ProductPageUserState,
    drawContent: @Composable () -> Unit
) {
    val signupDataState = state.productDataState.value
    signupDataState.apiState.ResolveState(
        loading = { ProgressDialog {} },
        success = { drawContent.invoke() },
        error = {
            showSnackBar(signupDataState.message, API_FAILED, SnackbarDuration.Short)
            state.productDataState.value.apiState = ApiState.Initial
        }
    )
    if (state.showInfo.value) DrawAlertDialog(
        title = stringResource(id = R.string.app_name),
        message = stringResource(id = R.string.app_desc),
        onConfirmation = { state.showInfo.value = false }
    )

    state.addToCartDataState.value.apiState.ResolveState(
        loading = { ProgressDialog {} },
        success = {
            showSnackBar(
                stringResource(id = R.string.add_to_cart_success),
                API_SUCCESS,
                SnackbarDuration.Short
            )
            state.addToCartDataState.value.apiState = ApiState.Initial
            navController.popBackStack()
            navController.navigate(Screen.CartScreen.route)
        },
        error = {
            showSnackBar(
                stringResource(id = R.string.database_error),
                API_FAILED,
                SnackbarDuration.Short
            )
            state.addToCartDataState.value.apiState = ApiState.Initial
        }
    )
}