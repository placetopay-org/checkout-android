package com.placetopay.p2pr.navigation

sealed class AppNavigation(val route: String) {
    object SplashScreen: AppNavigation("splash")
    object HomeScreen: AppNavigation("home")
    object DetailScreen: AppNavigation("detail")
    object ConfirmScreen: AppNavigation("confirm")
    object CheckOutScreen: AppNavigation("checkout")
    object ReceiptScreen: AppNavigation("receipt")
}