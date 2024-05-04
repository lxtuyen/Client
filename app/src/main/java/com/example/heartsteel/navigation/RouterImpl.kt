package com.example.heartsteel.navigation

import androidx.navigation.NavHostController
import com.example.heartsteel.tools.Ext.putArgs

class RouterImpl(
    private val navHostController: NavHostController,
    private val startDestination: String = Routes.ROUTE_HOME
) : Router {

    override fun goDetails(arg: Any?) {
        navigate(Screen.HomeDetails)
    }

    override fun goAddPersons() {
        navigate(Screen.AddPersons)
    }

    override fun goAddPodcasts() {
        navigate(Screen.AddPodcasts)
    }

    override fun goHome() {
        navigate(Screen.Home, removeFromHistory = true, singleTop = true)
    }

    override fun goHistory() {
        navigate(Screen.History)
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

    override fun goSplash() {
        navigate(Screen.Splash, true)
    }

    override fun goPlayerFull(arg: Any?) {
        navigate(Screen.PlayerFull)
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
                        popUpTo(Screen.Home.route)
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

    private fun checkArgsAndNavigate(it: Any?, screen: Screen): () -> Unit = {
        it?.let {
            navHostController.putArgs(Pair(screen.tag, it))
        }
        navigate(screen)
    }

    override fun <T : Any> getArgs(tag: String): T? {
        return try {
            navHostController.previousBackStackEntry?.arguments?.get(tag) as T?
        } catch (ex: Exception) {
            null
        }
    }

}