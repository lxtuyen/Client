package com.example.heartsteel.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.heartsteel.ui.theme.Active
import com.example.heartsteel.ui.theme.ActiveBorder

@Composable
fun ChipTag(
    modifier: Modifier = Modifier,
    selected: Boolean = false,
    text: String = "Empty",
    onChipSelected: () -> Unit = {},
) {
    Surface(
        color = when {
            selected -> Active
            else -> Color.Transparent
        },
        contentColor = Color.Transparent,
        shape = CircleShape,
        border = BorderStroke(
            width = 1.dp,
            color = when {
                selected -> ActiveBorder
                else -> Color.LightGray
            }
        ),
        modifier = modifier.clickable {
            onChipSelected()
        }
    ) {
        Text(
            text = text,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
            fontSize = 12.sp,
            color = Color.White,
            onTextLayout = {}
        )

    }
}

@Composable
@Preview
fun TagChipPreview() {
    ChipTag()
}