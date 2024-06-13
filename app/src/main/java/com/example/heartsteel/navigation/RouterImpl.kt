package com.example.heartsteel.navigation

import androidx.navigation.NavHostController

class RouterImpl(
    private val navHostController: NavHostController,
    private val startDestination: String = Routes.ROUTE_LOGIN
) : Router {

    override fun goDetails(arg: Any?) {
        navigate(Screen.HomeDetails)
    }

    override fun goAddPersons() {
        navigate(Screen.AddPersons)
    }

    override fun goHome() {
        navigate(Screen.Home, removeFromHistory = true, singleTop = true)
    }

    override fun goSearchTag() {
        navigate(Screen.SearchTag)
    }

    override fun goBack() {
        navHostController.apply {
            navigateUp()
            navigate(startDestination) {
                launchSingleTop = true
                restoreState = true
            }

        }
        navHostController.apply {

        }
    }

    override fun goSettings() {
        navigate(Screen.Settings)
    }

    override fun goNotification() {
        navigate(Screen.Notifications)
    }

    override fun goProfile() {
        navigate(Screen.Profile)
    }

    override fun goLogin() {
        navigate(Screen.Login)
    }

    override fun goSignup() {
        navigate(Screen.Signup)
    }

    override fun goSplash() {
        navigate(Screen.Splash, true)
    }

    override fun goPlayerFull(arg: Any?) {
        navigate(Screen.PlayerFull)
    }

    override fun goLib() {
        navigate(Screen.Libs)
    }

    private fun navigate(
        screen: Screen,
        removeFromHistory: Boolean = false,
        singleTop: Boolean = false
    ) {
        navHostController.apply {
            navigate(screen.route) {
                if (removeFromHistory) {
                    if (singleTop) {
                        popUpTo(Screen.Login.route)
                    } else {
                        popUpTo(0) {
                            saveState = false
                        }
                    }

                } else {
                    restoreState = true
                }
                launchSingleTop = singleTop
            }
        }
    }
}