package com.linoop.quickcart.navigation

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.linoop.quickcart.presentation.HomePageUI
import com.linoop.quickcart.presentation.SplashScreenUI
import com.linoop.quickcart.utils.ShowSnackBar
import com.linoop.quickcart.viewmodel.HomeViewModel
import com.linoop.quickcart.presentation.CartPageUI
import com.linoop.quickcart.presentation.ProductDetailsPageUI
import com.linoop.quickcart.transformation.HomeViewModelToUserEvent
import com.linoop.quickcart.transformation.HomeViewModelToUserState

@Composable
fun ScreenNav(
    context: Context,
    navHostController: NavHostController,
    showSnackBar: ShowSnackBar
) {
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

        composable(route = Screen.ProductDetailsScreen.route) {
            NavigateToProductDetailsScreen(navHostController, showSnackBar)
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
    CartPageUI()
}

@Composable
fun NavigateToProductDetailsScreen(
    navController: NavController,
    showSnackBar: ShowSnackBar
) {
    ProductDetailsPageUI()
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
