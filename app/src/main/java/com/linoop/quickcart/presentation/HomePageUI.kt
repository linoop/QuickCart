package com.linoop.quickcart.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.linoop.acronymapp.presentation.HomeViewState
import com.linoop.quickcart.utils.ShowSnackBar
import com.linoop.quickcart.widgets.MySnackBar
import com.linoop.quickcart.R
import com.linoop.quickcart.navigation.Screen
import com.linoop.quickcart.state.HomePageUserEvent
import com.linoop.quickcart.state.HomePageUserState
import com.linoop.quickcart.widgets.DrawTopAppBar

@Composable
fun HomePageUI(
    navController: NavController,
    showSnackBar: ShowSnackBar,
    userState: HomePageUserState,
    userEvent: HomePageUserEvent
) {
    LaunchedEffect(Unit) {
        userEvent.getProduct.invoke()
    }
    DrawHomePage(
        navController = navController,
        showSnackBar = showSnackBar,
        userState = userState,
        userEvent = userEvent
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DrawHomePage(
    showSnackBar: ShowSnackBar,
    navController: NavController,
    userState: HomePageUserState,
    userEvent: HomePageUserEvent
) {
    val snackBarState = remember { SnackbarHostState() }
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())

    val query = rememberSaveable { mutableStateOf("") }
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            DrawHomeTopAppBar(
                navController = navController,
                scrollBehavior = scrollBehavior,
                userEvent = userEvent
            )
        },
        snackbarHost = { MySnackBar(snackBarHostState = snackBarState) },
    ) {
        Column(
            modifier = Modifier
                .padding(it)
        ) {
            HomeViewState(showSnackBar, userState)
            DrawHomeContent(userState)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DrawHomeTopAppBar(
    scrollBehavior: TopAppBarScrollBehavior,
    navController: NavController,
    userEvent: HomePageUserEvent
) {
    DrawTopAppBar(
        scrollBehavior = scrollBehavior,
        title = stringResource(id = R.string.app_name),
        actionIcon = Icons.Default.ShoppingCart,
        actionOnClick = {
            userEvent.getProductList.invoke()
            // navController.navigate(Screen.CartScreen.route)
        }
    )
}

@Composable
fun DrawHomeContent(state: HomePageUserState) {
    val moviePagingItems = state.productPagingItems.collectAsLazyPagingItems()
    LazyColumn(modifier = Modifier.padding(10.dp)) {
        items(moviePagingItems.itemCount) { index ->
            Text(
                text = moviePagingItems[index]!!.title,
                color = MaterialTheme.colorScheme.primary
            )
        }
        moviePagingItems.apply {
            when {
                loadState.refresh is LoadState.Loading -> {
                    item { /*PageLoader(modifier = Modifier.fillParentMaxSize())*/ }
                }

                loadState.refresh is LoadState.Error -> {
                    val error = moviePagingItems.loadState.refresh as LoadState.Error
                    item {
                        /* ErrorMessage(
                             modifier = Modifier.fillParentMaxSize(),
                             message = error.error.localizedMessage!!,
                             onClickRetry = { retry() })*/
                    }
                }

                loadState.append is LoadState.Loading -> {
                    item { /*LoadingNextPageItem(modifier = Modifier)*/ }
                }

                loadState.append is LoadState.Error -> {
                    val error = moviePagingItems.loadState.append as LoadState.Error
                    item {
                        /*ErrorMessage(
                            modifier = Modifier,
                            message = error.error.localizedMessage!!,
                            onClickRetry = { retry() })*/
                    }
                }
            }
        }
    }
}


/*
@Composable
private fun DrawHomeContent(userState: HomePageUserState) {
    val products = userState.dataState.value.getProductsResponse.products
    if (products.isNotEmpty()) LazyColumn {
        items(products) { DrawProductCardView(it) }
    }
}*/
