package com.example.heartsteel.presentation

import androidx.activity.compose.BackHandler
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.heartsteel.components.NavigationBar
import com.example.heartsteel.components.PlayerSmall
import com.example.heartsteel.navigation.NavigationContainer
import com.example.heartsteel.navigation.Screen
import com.example.heartsteel.navigation.Router
import com.example.heartsteel.navigation.RouterImpl
import com.example.heartsteel.tools.Ext
import com.example.heartsteel.tools.Ext.clickableResize
import com.example.heartsteel.tools.Ext.gradient

@ExperimentalFoundationApi
@ExperimentalAnimationApi
@ExperimentalMaterial3Api
@Composable
fun MainScreen(finish: () -> Unit) {

    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val route = navBackStackEntry?.destination?.route ?: Screen.Splash.route
    val router: Router = remember { RouterImpl(navController, route) }
    val isFullScreen = Screen.isFullScreen(route)

    if (route == Screen.Login.route) {
        BackHandler {
            finish()
        }
    }
    Scaffold(
        bottomBar = {
            if (!isFullScreen) {
                Column(
                    modifier = Modifier
                        .gradient(
                            listOf(Color.Transparent, Color.Black),
                            Ext.GradientType.VERTICAL
                        )
                        .padding(top = 20.dp)
                ) {
                    PlayerSmall(
                        modifier = Modifier
                            .clickableResize {
                                router.goPlayerFull(null)
                            }
                    )
                    NavigationBar(route) { target ->
                        navController.apply {
                            navigate(target) {
                                restoreState = true
                                launchSingleTop = true
                                graph.startDestinationRoute?.let { route ->
                                    popUpTo(route = route) {
                                        saveState = true
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    ) {
        NavigationContainer(navController = navController, paddingValues = it, router = router)
    }
}

