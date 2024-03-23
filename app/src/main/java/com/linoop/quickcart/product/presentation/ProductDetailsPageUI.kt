package com.linoop.quickcart.product.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.ShoppingCart
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
import androidx.navigation.NavController
import com.linoop.quickcart.navigation.Screen
import com.linoop.quickcart.product.state.ProductPageUserEvent
import com.linoop.quickcart.product.state.ProductPageUserState
import com.linoop.quickcart.utils.ShowSnackBar
import com.linoop.quickcart.widgets.DrawTopAppBar
import com.linoop.quickcart.widgets.MySnackBar


@Composable
fun ProductDetailsPageUI(
    showSnackBar: ShowSnackBar,
    navController: NavController,
    userState: ProductPageUserState,
    userEvent: ProductPageUserEvent,
    productId: Int?
) {
    LaunchedEffect(key1 = Unit) {
        userEvent.getProductById.invoke(productId)
    }
    DrawProductDetails(showSnackBar, navController, userState, userEvent)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DrawProductDetails(
    showSnackBar: ShowSnackBar,
    navController: NavController,
    userState: ProductPageUserState,
    userEvent: ProductPageUserEvent
) {
    val snackBarState = remember { SnackbarHostState() }
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            DrawProductPageTopAppBar(
                navController = navController,
                scrollBehavior = scrollBehavior,
                userState = userState,
                userEvent = userEvent
            )
        },
        snackbarHost = { MySnackBar(snackBarHostState = snackBarState) },
    ) {
        Column(
            modifier = Modifier
                .padding(it)
        ) {
            ProductPageViewState(showSnackBar = showSnackBar, state = userState)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DrawProductPageTopAppBar(
    scrollBehavior: TopAppBarScrollBehavior,
    navController: NavController,
    userState: ProductPageUserState,
    userEvent: ProductPageUserEvent
) {
    DrawTopAppBar(
        scrollBehavior = scrollBehavior,
        title = userState.dataState.value.product.title,
        navigationIcon = Icons.Default.KeyboardArrowLeft,
        actionIcon = Icons.Default.ShoppingCart,
        navOnClick = { navController.popBackStack() },
        actionOnClick = { navController.navigate(Screen.CartScreen.route) }
    )
}