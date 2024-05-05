
package com.example.heartsteel.presentation

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.heartsteel.R
import com.example.heartsteel.ui.theme.Active
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(goBack: () -> Unit = {}) {
    BackHandler {
        goBack()
    }
    LaunchedEffect(true) {
        delay(1500)
        goBack()
    }
    Box(modifier = Modifier.fillMaxSize()) {
        Icon(
            modifier = Modifier.align(Alignment.Center),
            painter = painterResource(id = R.drawable.logo),
            contentDescription = null,
            tint = Active
        )
    }

}

@Preview
@Composable
fun SplashScreenPreview() {
    SplashScreen()
}