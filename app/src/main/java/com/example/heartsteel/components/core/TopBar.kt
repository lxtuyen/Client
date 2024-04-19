package com.example.heartsteel.components.core

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.heartsteel.components.IconBtn
import com.example.heartsteel.R
import com.example.heartsteel.components.TextTitle


@Composable
fun TopBar(
    modifier: Modifier = Modifier,
    verticalAlignment: Alignment.Vertical = Alignment.CenterVertically,
    navigationIcon: @Composable (() -> Unit)? = null,
    title: @Composable (RowScope.() -> Unit)?= null,
    actions: @Composable (RowScope.() -> Unit)? = null,
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        verticalAlignment = verticalAlignment
    ) {
        navigationIcon?.invoke()
        title?.invoke(this)
        actions?.invoke(this)
    }
}

@Preview
@Composable
fun TopBarPreview() {
   TopBar(
       navigationIcon = {
           IconBtn(resIcon = R.drawable.ic_left)
       },
       title = {
           TextTitle(
               modifier = Modifier.padding(horizontal = 8.dp),
               text = "title",
           )
       }
   )
}