package com.example.heartsteel.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

@Composable
fun TextSubtitle(
    modifier: Modifier = Modifier,
    text: String = "SubTitle",
    maxLines: Int = 1,
    color: Color = Color.Gray,
    fontSize: TextUnit = 12.sp,
    fontWeight: FontWeight = FontWeight.Light,
    textAlign: TextAlign? = null,
) {
    Text(
        modifier = modifier,
        text = text,
        color = color,
        fontSize = fontSize,
        fontWeight = fontWeight,
        textAlign = textAlign,
        maxLines = maxLines,
        onTextLayout = {}
    )
}