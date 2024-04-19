package com.example.heartsteel.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.heartsteel.components.IconBtn
import com.example.heartsteel.R
import com.example.heartsteel.ui.theme.Sizes.DEFAULT
import com.example.heartsteel.ui.theme.Sizes.SMALL
import com.example.heartsteel.ui.theme.Notification

@Composable
fun TopAppBar(
    modifier: Modifier = Modifier,
    title: String = "Good evening",
    onActionClicked: (pos: Int) -> Unit = {},
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = SMALL),
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextTitle(
            modifier = Modifier
                .padding(horizontal = DEFAULT)
                .weight(1f),
            text = title,
        )
        Box {
            IconBtn(resIcon = R.drawable.ic_notification) {
                onActionClicked(0)
            }
            Icon(
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(end = 14.dp, bottom = 12.dp),
                tint = Notification,
                painter = painterResource(id = R.drawable.ic_dot),
                contentDescription = null
            )
        }

        IconBtn(resIcon = R.drawable.ic_history) {
            onActionClicked(1)
        }
        IconBtn(resIcon = R.drawable.ic_settings) {
            onActionClicked(2)
        }
    }
}

@Preview
@Composable
fun TopAppBarPreview() {
    TopAppBar()
}