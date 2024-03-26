package com.linoop.quickcart.navigation

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.linoop.quickcart.home.presentation.HomePageUI
import com.linoop.quickcart.home.presentation.SplashScreenUI
import com.linoop.quickcart.utils.ShowSnackBar
import com.linoop.quickcart.home.viewmodel.HomeViewModel
import com.linoop.quickcart.cart.presentation.CartPageUI
import com.linoop.quickcart.cart.converter.CartViewModelToUserEvent
import com.linoop.quickcart.cart.converter.CartViewModelToUserState
import com.linoop.quickcart.cart.viewmodel.CartViewModel
import com.linoop.quickcart.product.presentation.ProductDetailsPageUI
import com.linoop.quickcart.home.converter.HomeViewModelToUserEvent
import com.linoop.quickcart.home.converter.HomeViewModelToUserState
import com.linoop.quickcart.product.converter.ProductViewModelToUserEvent
import com.linoop.quickcart.product.converter.ProductViewmodelToUserState
import com.linoop.quickcart.product.viewmodel.ProductViewModel
import com.linoop.quickcart.utils.Constants.PRODUCT_ID

@Composable
fun ScreenNav(
    context: Context,
    navHostController: NavHostController,
    showSnackBar: ShowSnackBar
) {
    val productDetailsArguments = listOf(
        navArgument(PRODUCT_ID) {
            type = NavType.IntType
            defaultValue = 0
        },
    )
    NavHost(
        navController = navHostController,
        startDestination = Screen.SplashScreen.route
    ) {
        composable(route = Screen.SplashScreen.route) {
            NavigateToSplashScreen(navHostController)
        }

        composable(route = Screen.HomeScreen.route) {
            NavigateToHomeScreen(navHostController, showSnackBar)
        }

        composable(
            route = Screen.ProductDetailsScreen.route.plus("/{$PRODUCT_ID}"),
            arguments = productDetailsArguments
        ) { entry ->
            NavigateToProductDetailsScreen(navHostController, showSnackBar, entry)
        }

        composable(route = Screen.CartScreen.route) {
            NavigateToCartScreen(navHostController, showSnackBar)
        }
    }
}

@Composable
fun NavigateToCartScreen(
    navController: NavController,
    showSnackBar: ShowSnackBar
) {
    val viewModel = hiltViewModel<CartViewModel>()
    val userState = CartViewModelToUserState().convert(viewModel)
    val userEvent = CartViewModelToUserEvent().convert(viewModel)
    CartPageUI(navController, showSnackBar, userState, userEvent)
}

@Composable
fun NavigateToProductDetailsScreen(
    navController: NavController,
    showSnackBar: ShowSnackBar,
    backStackEntry: NavBackStackEntry
) {
    val productId = backStackEntry.arguments?.getInt(PRODUCT_ID)
    val viewModel = hiltViewModel<ProductViewModel>()
    val userState = ProductViewmodelToUserState().convert(viewModel)
    val userEvent = ProductViewModelToUserEvent().convert(viewModel)
    ProductDetailsPageUI(
        navController = navController,
        showSnackBar = showSnackBar,
        userState = userState,
        userEvent = userEvent,
        productId = productId
    )
}

@Composable
fun NavigateToHomeScreen(
    navController: NavController,
    showSnackBar: ShowSnackBar
) {
    val viewModel = hiltViewModel<HomeViewModel>()
    val userState = HomeViewModelToUserState().convert(viewModel)
    val userEvent = HomeViewModelToUserEvent().convert(viewModel)
    HomePageUI(
        navController = navController,
        showSnackBar = showSnackBar,
        userState = userState,
        userEvent = userEvent
    )
}

@Composable
fun NavigateToSplashScreen(navController: NavController) {
    SplashScreenUI(navController = navController)
}
