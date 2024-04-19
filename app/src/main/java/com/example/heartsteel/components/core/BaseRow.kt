package com.example.heartsteel.components.core

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.heartsteel.R
import com.example.heartsteel.components.ImageCrop
import com.example.heartsteel.tools.Ext.color
import com.example.heartsteel.tools.Ext.round
import com.example.heartsteel.ui.theme.Placeholder

@Composable
fun BaseRaw(
    modifier: Modifier = Modifier,
    imageRes: Any? = R.drawable.album,
    imageSize: Dp = 100.dp,
    round: Dp? = null,
    roundPercent: Int = 0,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.Center,
    verticalAlignment: Alignment.Vertical = Alignment.CenterVertically,
    contentEnd: @Composable (RowScope.() -> Unit)? = null,
    content: @Composable ColumnScope.() -> Unit,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = horizontalArrangement,
        verticalAlignment = verticalAlignment
    ) {
        ImageCrop(
            modifier = Modifier
                .round(round, roundPercent)
                .size(imageSize)
                .color(Placeholder),
            data = imageRes,
        )
        Column(
            Modifier
                .padding(horizontal = 8.dp)
                .weight(2f)) {
            content(this)
        }
        contentEnd?.invoke(this)
    }

}