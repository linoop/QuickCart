package com.linoop.quickcart.cart.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.linoop.quickcart.R
import com.linoop.quickcart.cart.state.CartPageUserEvent
import com.linoop.quickcart.cart.state.CartPageUserState
import com.linoop.quickcart.cart.state.OpenCartDataState
import com.linoop.quickcart.ui.theme.QuickCartTheme
import com.linoop.quickcart.ui.theme.Red
import com.linoop.quickcart.utils.ApiState
import com.linoop.quickcart.utils.DummyData
import com.linoop.quickcart.utils.ShowSnackBar
import com.linoop.quickcart.utils.ViewState
import com.linoop.quickcart.widgets.DrawTopAppBar
import com.linoop.quickcart.widgets.MySnackBar

@Composable
fun CartPageUI(
    navController: NavController = rememberNavController(),
    showSnackBar: ShowSnackBar = { _, _, _ -> },
    userState: CartPageUserState = CartPageUserState(),
    userEvent: CartPageUserEvent = CartPageUserEvent()
) {
    LaunchedEffect(Unit) { userEvent.openCart.invoke() }
    DrawProductDetails(showSnackBar, navController, userState, userEvent)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DrawProductDetails(
    showSnackBar: ShowSnackBar,
    navController: NavController,
    userState: CartPageUserState,
    userEvent: CartPageUserEvent
) {
    val snackBarState = remember { SnackbarHostState() }
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = { DrawCartPageTopAppBar(navController, scrollBehavior) },
        snackbarHost = { MySnackBar(snackBarHostState = snackBarState) },
    ) {
        Box(modifier = Modifier.padding(it)) {
            CartPageViewState(
                showSnackBar = showSnackBar,
                state = userState,
                userEvent = userEvent,
                onCartEmpty = { DrawCartEmptyErrorView() },
                drawContent = { DrawCartPageContent(userState, userEvent) }
            )
        }
    }
}

@Composable
fun DrawCartPageContent(
    userState: CartPageUserState,
    userEvent: CartPageUserEvent
) {
    LazyColumn(modifier = Modifier.padding(vertical = dimensionResource(id = R.dimen.padding_small))) {
        items(userState.openCartDataState.value.products) { product ->
            DrawCartItemCardView(product) { userEvent.deleteFromCart.invoke(product) }
        }
    }
}

@Composable
private fun DrawCartEmptyErrorView() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(dimensionResource(id = R.dimen.padding_small)),
            verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
        ) {
        Image(
            modifier = Modifier.size(dimensionResource(id = R.dimen.box_size_large)),
            painter = painterResource(id = R.drawable.empty_cart),
            contentDescription = stringResource(id = R.string.cart_is_empty)
        )
        Text(
            text = stringResource(id = R.string.no_record_found),
            color = Red,
            textAlign = TextAlign.Center
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DrawCartPageTopAppBar(
    navController: NavController,
    scrollBehavior: TopAppBarScrollBehavior,
) {
    DrawTopAppBar(
        scrollBehavior = scrollBehavior,
        title = stringResource(id = R.string.cart),
        navigationIcon = Icons.Default.KeyboardArrowLeft,
        navOnClick = { navController.popBackStack() },
    )
}

@Preview
@Composable
private fun CartPageUIPreview() {
    val dataState = ViewState(OpenCartDataState(DummyData.productList, ApiState.Success))
    QuickCartTheme {
        CartPageUI(userState = CartPageUserState(openCartDataState = dataState))
    }
}