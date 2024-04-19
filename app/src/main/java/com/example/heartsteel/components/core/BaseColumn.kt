package com.example.heartsteel.components.core

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.heartsteel.R
import com.example.heartsteel.components.ImageCrop
import com.example.heartsteel.tools.Ext.round

@Composable
fun BaseColumn(
    modifier: Modifier = Modifier,
    imageRes: Any? = R.drawable.album,
    imageSize: Dp = 145.dp,
    round: Dp? = null,
    roundPercent: Int = 0,
    horizontalAlignment: Alignment.Horizontal = Alignment.CenterHorizontally,
    verticalArrangement: Arrangement.Vertical = Arrangement.Center,
    content: @Composable ColumnScope.() -> Unit,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = horizontalAlignment,
        verticalArrangement = verticalArrangement
    ) {
        ImageCrop(
            modifier = Modifier
                .round(round, roundPercent)
                .size(imageSize)
                //.color(Placeholder)
            ,
            data = imageRes,
        )
        content(this)
    }

}