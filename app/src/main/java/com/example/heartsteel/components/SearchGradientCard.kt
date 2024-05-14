package com.example.heartsteel.components

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import com.example.heartsteel.domain.model.Music
import com.example.heartsteel.tools.Ext.clickableResize
import com.example.heartsteel.tools.Ext.round

@Composable
fun SearchGradientCard(
    item: Music,
    onClick: () -> Unit = {}
) {
    val title = item.title
    val author = item.author
    val image = item.image

    Row(
        modifier = Modifier
            .padding(8.dp)
            .clickableResize(onClick)
            .height(90.dp)
            .round(8.dp)
            .clipToBounds(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        title?.let {
            TextTitle(
                text = it,
                modifier = Modifier
                    .padding(10.dp)
                    .weight(1f),
                color = Color.White,
            )
        }
        ImageCrop(
            data = image,
            modifier = Modifier
                .size(70.dp)
                .align(Alignment.Bottom)
                .graphicsLayer(translationX = 60f, rotationZ = 32f, shadowElevation = 15f)
        )
    }
}
