package com.linoop.quickcart.home.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import com.linoop.quickcart.utils.ShowSnackBar
import com.linoop.quickcart.widgets.MySnackBar
import com.linoop.quickcart.R
import com.linoop.quickcart.navigation.Screen
import com.linoop.quickcart.home.state.HomePageUserEvent
import com.linoop.quickcart.home.state.HomePageUserState
import com.linoop.quickcart.ui.theme.LightGray
import com.linoop.quickcart.ui.theme.QuickCartTheme
import com.linoop.quickcart.ui.theme.Red
import com.linoop.quickcart.utils.DummyData.productList
import com.linoop.quickcart.utils.onClick
import com.linoop.quickcart.widgets.DrawTopAppBar
import kotlinx.coroutines.flow.MutableStateFlow

@Composable
fun HomePageUI(
    navController: NavController = rememberNavController(),
    showSnackBar: ShowSnackBar = { _, _, _ -> },
    userState: HomePageUserState = HomePageUserState(),
    userEvent: HomePageUserEvent = HomePageUserEvent()
) {
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
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            DrawHomeTopAppBar(
                navController = navController,
                scrollBehavior = scrollBehavior,
                userState = userState
            )
        },
        snackbarHost = { MySnackBar(snackBarHostState = snackBarState) },
    ) {
        Column(modifier = Modifier.padding(it)) {
            HomeViewState(showSnackBar, userState)
            DrawHomeContent(
                navController = navController,
                userState = userState,
                userEvent = userEvent
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DrawHomeTopAppBar(
    scrollBehavior: TopAppBarScrollBehavior,
    navController: NavController,
    userState: HomePageUserState,
) {
    DrawTopAppBar(
        scrollBehavior = scrollBehavior,
        navigationIcon = Icons.Default.Info,
        title = stringResource(id = R.string.app_name),
        actionIcon = Icons.Default.ShoppingCart,
        actionOnClick = { navController.navigate(Screen.CartScreen.route) },
        navOnClick = { userState.showInfo.value = true }
    )
}

@Composable
fun DrawHomeContent(
    navController: NavController,
    userState: HomePageUserState,
    userEvent: HomePageUserEvent
) {
    val moviePagingItems = userState.productPagingItems.collectAsLazyPagingItems()
    LazyColumn(modifier = Modifier.padding(vertical = dimensionResource(id = R.dimen.padding_small))) {
        items(moviePagingItems.itemCount) { index ->
            moviePagingItems[index]?.let { product ->
                DrawProductCardView(product = product) { id ->
                    navController.navigate(Screen.ProductDetailsScreen.route.plus("/${id}"))
                }
            }
        }
        moviePagingItems.apply {
            when {
                loadState.refresh is LoadState.Loading -> {
                    item { DrawItemLoadingView() }
                }

                loadState.refresh is LoadState.Error -> {
                    //val error = moviePagingItems.loadState.refresh as LoadState.Error
                    item {
                        DrawItemLoadErrorView(
                            modifier = Modifier.fillMaxWidth(),
                            message = stringResource(id = R.string.product_list_load_error)
                        ) { userEvent.getProductList.invoke() }
                    }
                }

                loadState.append is LoadState.Loading -> {
                    item { DrawItemLoadingView() }
                }

                loadState.append is LoadState.Error -> {
                    //val error = moviePagingItems.loadState.append as LoadState.Error
                    item {
                        DrawItemLoadErrorView(
                            modifier = Modifier.fillMaxWidth(),
                            message = stringResource(id = R.string.product_list_load_error)
                        ) { userEvent.getProductList.invoke() }
                    }
                }
            }
        }
    }
}

@Composable
private fun DrawItemLoadingView() {
    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator(color = LightGray)
    }
}

@Composable
private fun DrawItemLoadErrorView(modifier: Modifier, message: String?, retry: onClick) {
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = message.toString(), color = Red, textAlign = TextAlign.Center)
        TextButton(modifier = Modifier, onClick = { retry.invoke() }) {
            Text(text = stringResource(id = R.string.retry))
        }
    }
}

@Preview
@Composable
private fun HomePageUIPreview() {
    val testData = PagingData.from(productList)
    val userState = HomePageUserState(productPagingItems = MutableStateFlow(testData))
    QuickCartTheme { HomePageUI(userState = userState) }
}