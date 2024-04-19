package com.example.heartsteel.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.heartsteel.components.core.BaseColumn
import com.example.heartsteel.model.Music
import com.example.heartsteel.tools.Ext.clickableResize
import com.example.heartsteel.ui.theme.Sizes.MEDIUM

@Composable
fun CardColumn(
    imageSize: Dp = 145.dp,
    round: Dp? = null,
    roundPercent: Int = 0,
    item: Music = Music.Default,
    horizontalAlignment: Alignment.Horizontal = Alignment.CenterHorizontally,
    verticalArrangement: Arrangement.Vertical = Arrangement.Center,
    onClick: (Music) -> Unit = {},
) {
    var finalAlignment = horizontalAlignment
    if (round != null) {
        finalAlignment = Alignment.Start
    }
    val title = item.title
    val subtitle = item.subtitle
    val tag = item.tag
    val description = item.description
    var paddingSubtitle = 0.dp
    if (title == null && tag == null) {
        paddingSubtitle = 8.dp
    }
    BaseColumn(
        modifier = Modifier
            .padding(MEDIUM)
            .width(imageSize)
            .clickableResize { onClick(item) },
        imageSize = imageSize,
        imageRes = item.image,
        round = round,
        roundPercent = roundPercent,
        horizontalAlignment = finalAlignment,
        verticalArrangement = verticalArrangement,
    ) {

        tag?.let {
            TextTag(text = it, modifier = Modifier.padding(top = 8.dp))
        }
        title?.let {
            TextTitle(text = it)
        }
        subtitle?.let {
            TextSubtitle(text = it, modifier = Modifier.padding(top = paddingSubtitle))
        }
        description?.let {
            TextSubtitle(text = it, maxLines = 5)
        }
    }
}