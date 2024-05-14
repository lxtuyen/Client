package com.example.heartsteel.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.heartsteel.tools.Ext.color
import com.example.heartsteel.tools.Ext.clickableResize
import com.example.heartsteel.tools.Ext.round
import com.example.heartsteel.ui.theme.Placeholder

@Composable
fun SmallCardItem(
    modifier: Modifier = Modifier,
    image: String? = "",
    title: String = "Empty title",
    onClick: () -> Unit = {}
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .padding(4.dp)
            .clickableResize {
                onClick()
            }
            .round(8.dp)
            .clipToBounds()
            .color(Placeholder)
    ) {
        ImageCrop(
            data = image,
            modifier = Modifier.size(58.dp)
        )
         Text(
            text = title,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.padding(horizontal = 8.dp),
             onTextLayout = {}
        )
    }
}