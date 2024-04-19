package com.example.heartsteel.components

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.heartsteel.components.core.BaseRaw
import com.example.heartsteel.model.Music
import com.example.heartsteel.tools.Ext.clickableResize
import com.example.heartsteel.ui.theme.Sizes.MEDIUM

@Composable
fun CardRow(
    imageSize: Dp = 55.dp,
    round: Dp? = null,
    roundPercent: Int = 0,
    item: Music = Music.Default,
    onClick: (Music) -> Unit = {},
) {
    BaseRaw(
        modifier = Modifier
            .padding(MEDIUM)
            .clickableResize {
                onClick(item)
            },
        imageSize = imageSize,
        imageRes = item.image,
        round = round,
        roundPercent = roundPercent,
        content = {
            item.subtitle?.let {
                TextSubtitle(
                    text = it
                )
            }
            item.title?.let {
                TextTitle(
                    text = it,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        }
    )
}