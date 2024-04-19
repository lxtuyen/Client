package com.example.heartsteel.navigation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.heartsteel.screens.AddPersonsScreen
import com.example.heartsteel.screens.AddPodcastsScreen
import com.example.heartsteel.screens.DetailsScreen
import com.example.heartsteel.screens.HomeScreen
import com.example.heartsteel.screens.LibsScreen
import com.example.heartsteel.screens.LoginScreen
import com.example.heartsteel.screens.NotificationsScreen
import com.example.heartsteel.screens.PlayerFullScreen
import com.example.heartsteel.screens.PremiumScreen
import com.example.heartsteel.screens.SearchScreen
import com.example.heartsteel.screens.SplashScreen

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
        if (startDestination.value == Screen.Home.route) {
            router.goHome()
        }
    }
    NavHost(
        navController = navController,
        startDestination = startDestination.value,
    ) {
        composable(Screen.Home.route) {
            HomeScreen(paddingValues, router)
        }
        composable(Screen.Search.route) {
            SearchScreen(paddingValues)
        }
        composable(Screen.Libs.route) {
            LibsScreen(paddingValues, router)
        }
        composable(Screen.Premium.route) {
            PremiumScreen(paddingValues)
        }
        composable(Screen.Splash.route) {
            SplashScreen(
                goBack = {
                    startDestination.value = Screen.Home.route
                }
            )
        }
        composable(Screen.HomeDetails.route) {
            DetailsScreen(paddingValues)
        }

        composable(Screen.Notifications.route) {
            NotificationsScreen(paddingValues)
        }
        composable(Screen.Settings.route) {
            NotificationsScreen(paddingValues)
        }
        composable(Screen.Profile.route) {
            NotificationsScreen(paddingValues)
        }
        composable(Screen.History.route) {
            NotificationsScreen(paddingValues)
        }
        composable(Screen.AddPodcasts.route) {
            AddPodcastsScreen()
        }
        composable(Screen.AddPersons.route) {
            AddPersonsScreen()
        }
        composable(Screen.PlayerFull.route) {
            PlayerFullScreen()
        }
        composable(Screen.Login.route) {
            LoginScreen({},{})
        }
    }
}