package com.example.heartsteel.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.heartsteel.R
import com.example.heartsteel.tools.Ext.clickableResize
import com.example.heartsteel.tools.Ext.round
import com.example.heartsteel.tools.Ext.vibrantColor



@Composable
fun SearchGradientCard(
    image: Int? = null,
    title: String = "Title",
    onClick: () -> Unit = {}
) {
    val res = image ?: R.drawable.album
    val context = LocalContext.current
    val stateColors: MutableState<Color> =
        remember(res) { mutableStateOf(Color.Transparent) }

    val imageBitmap = remember(res) {
        ImageBitmap.imageResource(context.resources, res).asAndroidBitmap()
    }

    remember(res) {
        imageBitmap.vibrantColor { stateColors.value = it }
    }

    Row(
        modifier = Modifier
            .padding(8.dp)
            .clickableResize(onClick)
            .height(90.dp)
            .round(8.dp)
            .clipToBounds()
            .background(stateColors.value),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        TextTitle(
            text = title,
            modifier = Modifier
                .padding(10.dp)
                .weight(1f),
            color = Color.White,
        )
        ImageCrop(
            data = res,
            modifier = Modifier
                .size(70.dp)
                .align(Alignment.Bottom)
                .graphicsLayer(translationX = 60f, rotationZ = 32f, shadowElevation = 15f)
        )
    }
}

@ExperimentalComposeUiApi
@Preview
@Composable
fun SearchGradientPreview() {
    SearchGradientCard()
}