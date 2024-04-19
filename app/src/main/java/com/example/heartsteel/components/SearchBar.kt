package com.example.heartsteel.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.heartsteel.R
import com.example.heartsteel.ui.theme.Sizes.DEFAULT
import com.example.heartsteel.ui.theme.Sizes.SMALL

@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    title: String = "Title",
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(4.dp))
            .background(Color.White)
            .padding(vertical = SMALL, horizontal = SMALL),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconBtn(
            resIcon = R.drawable.ic_search_small,
            tint = Color.Black,
            modifier = Modifier
                .size(40.dp)
                .padding(10.dp)
        )
        TextTitle(
            modifier = Modifier
                .padding(horizontal = DEFAULT)
                .weight(1f),
            text = title,
            color = Color.DarkGray,
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Preview
@Composable
fun SearchBarPreview() {
    SearchBar()
}