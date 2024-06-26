package com.linoop.quickcart.navigation

/**
 * Screen class contains all available screens in QuickCart App
 */
sealed class Screen(val route: String) {
    data object SplashScreen : Screen("splash")
    data object HomeScreen : Screen("home")
    data object ProductDetailsScreen : Screen("product_details")
    data object CartScreen : Screen("cart")
}