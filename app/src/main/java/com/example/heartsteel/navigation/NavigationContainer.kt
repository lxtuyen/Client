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
import com.example.heartsteel.presentation.login.LoginScreen
import com.example.heartsteel.presentation.SplashScreen
import com.example.heartsteel.presentation.addPersons.AddPersonsScreen
import com.example.heartsteel.presentation.addPodcasts.AddPodcastsScreen
import com.example.heartsteel.presentation.detail.DetailsScreen
import com.example.heartsteel.presentation.history.HistoryScreen
import com.example.heartsteel.presentation.home.HomeScreen
import com.example.heartsteel.presentation.libs.LibsScreen
import com.example.heartsteel.presentation.notifications.NotificationsScreen
import com.example.heartsteel.presentation.playerFull.PlayerFullScreen
import com.example.heartsteel.presentation.premium.PremiumScreen
import com.example.heartsteel.presentation.profile.ProfileScreen
import com.example.heartsteel.presentation.search.SearchScreen
import com.example.heartsteel.presentation.settings.SettingsScreen
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
                    startDestination.value = Screen.Login.route
                }
            )
        }
        composable(Screen.HomeDetails.route) {
            DetailsScreen(paddingValues)
        }

        composable(Screen.Notifications.route) {
            NotificationsScreen(router, paddingValues)
        }
        composable(Screen.Settings.route) {
            SettingsScreen()
        }
        composable(Screen.Profile.route) {
            ProfileScreen()
        }
        composable(Screen.History.route) {
            HistoryScreen()
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
            LoginScreen(router)
        }
        composable(Screen.Signup.route) {
            SignupScreen(router)
        }
    }
}