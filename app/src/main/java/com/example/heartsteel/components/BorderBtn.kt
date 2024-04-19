package com.example.heartsteel.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.heartsteel.R


@Composable
fun BorderBtn(
    modifier: Modifier = Modifier,
    resIcon: Int = R.drawable.ic_baseline_close_24,
    tint: Color = Color.LightGray,
    onClick: () -> Unit = {},
) {
    Surface(
        color = Color.Transparent,
        contentColor = Color.Transparent,
        shape = CircleShape,
        border = BorderStroke(
            width = 1.dp,
            color = Color.LightGray
        ),
        modifier = modifier
            .clickable {
            onClick()
        }
    ) {

        Icon(
            modifier = Modifier.padding(4.dp),
            painter = painterResource(id = resIcon),
            contentDescription = null,
            tint = tint
        )

    }
}


@Composable
@Preview
fun PreviewBorderBtn() {
    BorderBtn()
}