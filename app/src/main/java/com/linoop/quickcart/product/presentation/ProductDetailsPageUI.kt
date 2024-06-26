package com.linoop.quickcart.product.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.linoop.quickcart.R
import com.linoop.quickcart.navigation.Screen
import com.linoop.quickcart.product.state.ProductPageDataState
import com.linoop.quickcart.product.state.ProductPageUserEvent
import com.linoop.quickcart.product.state.ProductPageUserState
import com.linoop.quickcart.ui.theme.QuickCartTheme
import com.linoop.quickcart.ui.theme.Red
import com.linoop.quickcart.utils.ApiState
import com.linoop.quickcart.utils.DummyData
import com.linoop.quickcart.utils.ShowSnackBar
import com.linoop.quickcart.utils.ViewState
import com.linoop.quickcart.utils.onClick
import com.linoop.quickcart.widgets.DrawTopAppBar
import com.linoop.quickcart.widgets.ImageViewPager
import com.linoop.quickcart.widgets.MySnackBar

@Composable
fun ProductDetailsPageUI(
    showSnackBar: ShowSnackBar = { _, _, _ -> },
    navController: NavController = rememberNavController(),
    userState: ProductPageUserState = ProductPageUserState(),
    userEvent: ProductPageUserEvent = ProductPageUserEvent(),
    productId: Int? = null,
) {
    LaunchedEffect(key1 = Unit) { userEvent.getProductById.invoke(productId) }

    DrawProductDetails(showSnackBar, navController, userState, userEvent, productId)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DrawProductDetails(
    showSnackBar: ShowSnackBar,
    navController: NavController,
    userState: ProductPageUserState,
    userEvent: ProductPageUserEvent,
    productId: Int?
) {
    val snackBarState = remember { SnackbarHostState() }
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = { DrawProductPageTopAppBar(scrollBehavior, navController, userState) },
        snackbarHost = { MySnackBar(snackBarHostState = snackBarState) },
    ) {
        Box(modifier = Modifier.padding(it)) {
            ProductPageViewState(
                navController = navController,
                showSnackBar = showSnackBar,
                state = userState,
                onApiFailed = { DrawProductLoadErrorView { userEvent.getProductById.invoke(productId) } }
            ) {
                DrawProductPageContent(userState, userEvent)
            }
        }
    }
}

@Composable
private fun DrawProductPageContent(
    userState: ProductPageUserState,
    userEvent: ProductPageUserEvent
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        DrawProductImagePager(imageUrls = userState.productDataState.value.product.images)
        DrawProductDetailsCardView(product = userState.productDataState.value.product)
        DrawAddToCartButton(modifier = Modifier, userState = userState, userEvent = userEvent)
    }
}


@Composable
private fun DrawProductImagePager(imageUrls: List<String>?) {
    if (!imageUrls.isNullOrEmpty()) ImageViewPager(
        modifier = Modifier
            .fillMaxWidth()
            .height(dimensionResource(id = R.dimen.box_size_xl)),
        imageUrls = imageUrls
    )
}

@Composable
fun DrawAddToCartButton(
    modifier: Modifier,
    userEvent: ProductPageUserEvent,
    userState: ProductPageUserState
) {
    OutlinedButton(
        shape = RoundedCornerShape(dimensionResource(id = R.dimen.corner_radius_medium)),
        modifier = modifier
            .fillMaxWidth()
            .padding(dimensionResource(id = R.dimen.padding_medium))
            .height(dimensionResource(id = R.dimen.filled_button_height)),
        onClick = { userEvent.addToCart.invoke(userState.productDataState.value.product) },
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = colorResource(id = R.color.button_color)
        ),
        elevation = ButtonDefaults.buttonElevation(dimensionResource(id = R.dimen.elevation_medium))
    ) {
        Text(
            text = stringResource(id = R.string.add_to_cart),
            style = MaterialTheme.typography.bodySmall.copy(color = Color.White),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun DrawProductLoadErrorView(retry: onClick) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(dimensionResource(id = R.dimen.padding_small)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            modifier = Modifier.size(dimensionResource(id = R.dimen.box_size_large)),
            painter = painterResource(id = R.drawable.cloud_error),
            contentDescription = stringResource(id = R.string.connection_error)
        )
        Text(
            text = stringResource(id = R.string.product_details_load_error),
            color = Red,
            textAlign = TextAlign.Center
        )
        TextButton(modifier = Modifier, onClick = { retry.invoke() }) {
            Text(text = stringResource(id = R.string.retry))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DrawProductPageTopAppBar(
    scrollBehavior: TopAppBarScrollBehavior,
    navController: NavController,
    userState: ProductPageUserState,
) {
    DrawTopAppBar(
        scrollBehavior = scrollBehavior,
        title = userState.productDataState.value.product.title,
        navigationIcon = Icons.Default.KeyboardArrowLeft,
        actionIcon = Icons.Default.ShoppingCart,
        navOnClick = { navController.popBackStack() },
        actionOnClick = { navController.navigate(Screen.CartScreen.route) }
    )
}

@Preview
@Composable
private fun ProductDetailsPageUIPreview() {
    val productDataState = ViewState(ProductPageDataState(DummyData.product, ApiState.Success))
    val state = ProductPageUserState(productDataState = productDataState)
    QuickCartTheme { ProductDetailsPageUI(userState = state) }
}