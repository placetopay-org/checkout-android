package com.placetopay.p2pr.compose

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.placetopay.p2pr.compose.buyer.BuyerScreen
import com.placetopay.p2pr.compose.checkout.CheckoutScreen
import com.placetopay.p2pr.compose.confirm.ConfirmScreen
import com.placetopay.p2pr.compose.home.HomeScreen
import com.placetopay.p2pr.compose.receipt.ReceiptScreen
import com.placetopay.p2pr.compose.splash.SplashScreen
import com.placetopay.p2pr.navigation.AppNavigation

@Composable
fun RedirectionApp() {
    val navController = rememberNavController()
    RedirectionNavHost(
        navController = navController
    )
}

@Composable
fun RedirectionNavHost(navController: NavHostController) {
    NavHost(navController = navController, startDestination = AppNavigation.SplashScreen.route) {
        composable(AppNavigation.SplashScreen.route) {
            SplashScreen(navController = navController)
        }
        composable(AppNavigation.HomeScreen.route) {
            HomeScreen(
                onPackageClick = {
                    navController.navigate("${AppNavigation.DetailScreen.route}/${it.id}")
                })
        }
        composable(
            route = "${AppNavigation.DetailScreen.route}/{packageId}",
            arguments = listOf(navArgument("packageId") { type = NavType.IntType })
        ) {
            BuyerScreen(
                onBackClick = { navController.navigateUp() },
                onConfirmClick = { welcomePackage, buyerPackage ->
                    navController.navigate("${AppNavigation.ConfirmScreen.route}/${welcomePackage.id}/${buyerPackage.id}")
                })
        }

        composable(
            route = "${AppNavigation.ConfirmScreen.route}/{packageId}/{buyerId}",
            arguments = listOf(navArgument("packageId") { type = NavType.IntType },
                navArgument("buyerId") { type = NavType.IntType })
        ) {
            ConfirmScreen(
                onBackClick = { navController.navigateUp() },
                onProcessUrl = { requestId, url ->
                    navController.navigate("${AppNavigation.CheckOutScreen.route}/$requestId/$url")
                })
        }

        composable(
            route = "${AppNavigation.CheckOutScreen.route}/{requestId}/{processUrl}",
            arguments = listOf(navArgument("requestId") { type = NavType.StringType },
                navArgument("processUrl") { type = NavType.StringType })
        ) {
            CheckoutScreen(
                onFinished = { processId ->
                    navController.popBackStack(AppNavigation.HomeScreen.route, false)
                    navController.navigate("${AppNavigation.ReceiptScreen.route}/$processId")
                })
        }

        composable(
            route = "${AppNavigation.ReceiptScreen.route}/{requestId}",
            arguments = listOf(navArgument("requestId") { type = NavType.StringType })
        ) {
            ReceiptScreen(
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}
