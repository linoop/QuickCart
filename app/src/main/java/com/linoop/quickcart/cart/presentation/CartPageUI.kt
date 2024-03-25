package com.linoop.quickcart.cart.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.linoop.quickcart.R
import com.linoop.quickcart.cart.state.CartPageUserEvent
import com.linoop.quickcart.cart.state.CartPageUserState
import com.linoop.quickcart.utils.ShowSnackBar
import com.linoop.quickcart.widgets.DrawTopAppBar
import com.linoop.quickcart.widgets.MySnackBar

@Composable
fun CartPageUI(
    navController: NavController,
    showSnackBar: ShowSnackBar,
    userState: CartPageUserState,
    userEvent: CartPageUserEvent
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
        topBar = {
            DrawCartPageTopAppBar(
                navController = navController,
                scrollBehavior = scrollBehavior,
                userState = userState,
                userEvent = userEvent
            )
        },
        snackbarHost = { MySnackBar(snackBarHostState = snackBarState) },
    ) {
        Box(modifier = Modifier.padding(it)) {
            CartPageViewState(
                navController = navController,
                showSnackBar = showSnackBar,
                state = userState,
                userEvent = userEvent
            ) {
                DrawCartPageContent(userState, userEvent)
            }
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DrawCartPageTopAppBar(
    navController: NavController,
    scrollBehavior: TopAppBarScrollBehavior,
    userState: CartPageUserState,
    userEvent: CartPageUserEvent
) {
    DrawTopAppBar(
        scrollBehavior = scrollBehavior,
        title = stringResource(id = R.string.cart),
        navigationIcon = Icons.Default.KeyboardArrowLeft,
        navOnClick = { navController.popBackStack() },
    )
}
