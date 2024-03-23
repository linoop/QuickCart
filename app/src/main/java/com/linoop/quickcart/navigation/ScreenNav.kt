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
import com.linoop.quickcart.cart.transformation.CartViewModelToUserEvent
import com.linoop.quickcart.cart.transformation.CartViewModelToUserState
import com.linoop.quickcart.cart.viewmodel.CartViewModel
import com.linoop.quickcart.product.presentation.ProductDetailsPageUI
import com.linoop.quickcart.home.transformation.HomeViewModelToUserEvent
import com.linoop.quickcart.home.transformation.HomeViewModelToUserState
import com.linoop.quickcart.product.transformation.ProductViewModelToUserEvent
import com.linoop.quickcart.product.transformation.ProductViewmodelToUserState
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
    val userState = CartViewModelToUserState().transform(viewModel)
    val userEvent = CartViewModelToUserEvent().transform(viewModel)
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
    val userState = ProductViewmodelToUserState().transform(viewModel)
    val userEvent = ProductViewModelToUserEvent().transform(viewModel)
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
    val userState = HomeViewModelToUserState().transform(viewModel)
    val userEvent = HomeViewModelToUserEvent().transform(viewModel)
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
