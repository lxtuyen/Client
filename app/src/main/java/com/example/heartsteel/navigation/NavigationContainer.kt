package com.example.heartsteel.navigation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.heartsteel.presentation.login.LoginScreen
import com.example.heartsteel.presentation.SplashScreen
import com.example.heartsteel.presentation.addMusic.AddPersonsScreen
import com.example.heartsteel.presentation.detail.DetailsScreen
import com.example.heartsteel.presentation.history.HistoryScreen
import com.example.heartsteel.presentation.home.HomeScreen
import com.example.heartsteel.presentation.libs.LibsScreen
import com.example.heartsteel.presentation.notifications.NotificationsScreen
import com.example.heartsteel.presentation.playerFull.PlayerFullScreen
import com.example.heartsteel.presentation.reel.ReelScreen
import com.example.heartsteel.presentation.search.SearchScreen
import com.example.heartsteel.presentation.signup.SignupScreen

@ExperimentalFoundationApi
@ExperimentalMaterial3Api
@Composable
fun NavigationContainer(
    router: Router,
    navController: NavHostController,
    paddingValues: PaddingValues,
) {
    val startDestination = remember { mutableStateOf(Screen.Splash.route) }
    LaunchedEffect(startDestination) {
        if (startDestination.value == Screen.Login.route) {
            router.goLogin()
        }
    }
    NavHost(
        navController = navController,
        startDestination = startDestination.value,
    ) {
        composable(Screen.Home.route) {
            HomeScreen(paddingValues, router,navController)
        }
        composable(Screen.Search.route) {
            SearchScreen(paddingValues,navController)
        }
        composable(Screen.Libs.route) {
            LibsScreen(paddingValues, router,navController)
        }
        composable(Screen.Reel.route) {
            ReelScreen()
        }
        composable(Screen.Splash.route) {
            SplashScreen(
                goBack = {
                    startDestination.value = Screen.Login.route
                }
            )
        }
        composable(Screen.HomeDetails.route+ "/{id}",arguments = listOf(navArgument("id"){type= NavType.StringType})) {
            val id = it.arguments?.getString("id")
            DetailsScreen(paddingValues, id.toString(),navController)
        }

        composable(Screen.Notifications.route) {
            NotificationsScreen(router, paddingValues,navController)
        }
        composable(Screen.History.route) {
            HistoryScreen()
        }
        composable(Screen.AddPersons.route) {
            AddPersonsScreen(router)
        }
        composable(Screen.PlayerFull.route+ "/{id}",arguments = listOf(navArgument("id"){type= NavType.StringType})) {
            val id = it.arguments?.getString("id")
            PlayerFullScreen(router, id.toString(),navController)
        }
        composable(Screen.Login.route) {
            LoginScreen(router)
        }
        composable(Screen.Signup.route) {
            SignupScreen(router)
        }
    }
}