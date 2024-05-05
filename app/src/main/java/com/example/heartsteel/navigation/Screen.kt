package com.example.heartsteel.navigation

import androidx.annotation.DrawableRes
import com.example.heartsteel.R
import com.example.heartsteel.navigation.Routes.ROUTE_HISTORY
import com.example.heartsteel.navigation.Routes.ROUTE_HOME
import com.example.heartsteel.navigation.Routes.ROUTE_HOME_DETAILS
import com.example.heartsteel.navigation.Routes.ROUTE_LIBS
import com.example.heartsteel.navigation.Routes.ROUTE_LOGIN
import com.example.heartsteel.navigation.Routes.ROUTE_SIGNUP
import com.example.heartsteel.navigation.Routes.ROUTE_NOTIFICATION
import com.example.heartsteel.navigation.Routes.ROUTE_PERSONS
import com.example.heartsteel.navigation.Routes.ROUTE_PLAYER_FULL
import com.example.heartsteel.navigation.Routes.ROUTE_PODCASTS
import com.example.heartsteel.navigation.Routes.ROUTE_PREMIUM
import com.example.heartsteel.navigation.Routes.ROUTE_PROFILE
import com.example.heartsteel.navigation.Routes.ROUTE_SEARCH
import com.example.heartsteel.navigation.Routes.ROUTE_SETTINGS
import com.example.heartsteel.navigation.Routes.ROUTE_SPLASH
import com.example.heartsteel.navigation.Routes.fullScreenRoutes

object Routes {
    const val ROUTE_HOME = "ROUTE_HOME"
    const val ROUTE_SEARCH = "ROUTE_SEARCH"
    const val ROUTE_LIBS = "ROUTE_LIBS"
    const val ROUTE_PREMIUM = "ROUTE_PREMIUM"
    const val ROUTE_HOME_DETAILS = "ROUTE_HOME_DETAILS"
    const val ROUTE_SPLASH = "ROUTE_SPLASH"
    const val ROUTE_PERSONS = "ROUTE_PERSONS"
    const val ROUTE_PODCASTS = "ROUTE_PODCASTS"
    const val ROUTE_PLAYER_FULL = "ROUTE_PLAYER_FULL"
    const val ROUTE_NOTIFICATION = "ROUTE_NOTIFICATION"
    const val ROUTE_SETTINGS = "ROUTE_SETTINGS"
    const val ROUTE_HISTORY = "ROUTE_HISTORY"
    const val ROUTE_PROFILE = "ROUTE_PROFILE"
    const val ROUTE_LOGIN = "ROUTE_LOGIN"
    const val ROUTE_SIGNUP = "ROUTE_SIGNUP"

    val fullScreenRoutes = listOf(
        ROUTE_SPLASH,
        ROUTE_PERSONS,
        ROUTE_PODCASTS,
        ROUTE_PLAYER_FULL,
        ROUTE_LOGIN,
        ROUTE_SIGNUP
    )
}

sealed class Screen(
    val route: String,
    var tag: String = route,
    val title: String = "",
    @DrawableRes val icon: Int = 0
) {

    data object Home : Screen(route = ROUTE_HOME, title = "Home", icon = R.drawable.ic_home_filled)
    data object Search : Screen(route = ROUTE_SEARCH, title = "Search", icon = R.drawable.ic_search_big)
    data object Libs :
        Screen(route = ROUTE_LIBS, title = "Your Library", icon = R.drawable.ic_library_big)

    data object Premium : Screen(route = ROUTE_PREMIUM, title = "Premium", icon = R.drawable.ic_premium)

    data object Splash : Screen(route = ROUTE_SPLASH)
    data object HomeDetails : Screen(route = ROUTE_HOME_DETAILS)
    data object PlayerFull : Screen(route = ROUTE_PLAYER_FULL)
    data object AddPersons : Screen(route = ROUTE_PERSONS)
    data object AddPodcasts : Screen(route = ROUTE_PODCASTS)
    data object Notifications : Screen(route = ROUTE_NOTIFICATION)
    data object Profile : Screen(route = ROUTE_PROFILE)
    data object Settings : Screen(route = ROUTE_SETTINGS)
    data object History : Screen(route = ROUTE_HISTORY)
    data object Login : Screen(route = ROUTE_LOGIN)
    data object Signup : Screen(route = ROUTE_SIGNUP)

    companion object {
        fun isFullScreen(route: String?): Boolean {
            return fullScreenRoutes.contains(route)
        }
    }

}