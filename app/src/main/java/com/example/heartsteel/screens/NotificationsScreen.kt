package com.example.heartsteel.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.heartsteel.R
import com.example.heartsteel.components.*
import com.example.heartsteel.components.core.TopBar
import com.example.heartsteel.navigation.Router
import com.example.heartsteel.repository.DataProvider
import com.example.heartsteel.tools.Ext.offsetY

@Composable
fun NotificationsScreen(paddingValues: PaddingValues = PaddingValues(),router: Router? = null) {
    val contentHeight = 150.dp
    val scrollState = rememberLazyListState()
    val offsetY = scrollState.offsetY(contentHeight)
    val alpha = 1f - offsetY.value / contentHeight.value
    val tracks = remember {
        DataProvider.itemsBy(4, 20)
    }
    val tracksNew = tracks.subList(0, 4)
    val chipState = remember {
        mutableIntStateOf(-1)
    }
    val chipSelected = chipState.intValue != -1
    val chips = remember { DataProvider.tags() }

    val goBack: () -> Unit = {
        router?.goHome()
    }

    TopBar(
        navigationIcon = {
            IconBtn(resIcon = R.drawable.ic_left, onClick = goBack )
        },
        title = {
            Text(
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .alpha(1 - alpha),
                text = "Whats new",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                onTextLayout = {}
            )
        },
    )

    LazyColumn(
        modifier = Modifier.padding(start = 10.dp, end = 10.dp, top = 50.dp),
        state = scrollState,
        contentPadding = PaddingValues(
            bottom = paddingValues.calculateBottomPadding(),
        )
    ) {
        item {
            Box(modifier = Modifier.height(contentHeight)) {
                TextTitle(
                    "Whats new", modifier = Modifier
                        .alpha(alpha)
                        .padding(vertical = 20.dp, horizontal = 8.dp)
                )
                LazyRow(
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .align(Alignment.BottomStart),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (chipSelected) {
                        item {
                            BorderBtn(modifier = Modifier.padding(end = 8.dp)) {
                                chipState.intValue = -1
                            }
                        }
                        item {
                            val selectedChip = chips[chipState.intValue]
                            ChipTag(selected = true, text = selectedChip) {
                                chipState.intValue = -1
                            }
                        }
                    } else {
                        itemsIndexed(chips) { index, chip ->
                            ChipTag(modifier = Modifier.padding(end = 8.dp), text = chip) {
                                chipState.intValue = index
                            }
                        }
                    }
                }
            }
        }
        item {
            TextTitle("New", modifier = Modifier.padding(8.dp))
        }
        itemsIndexed(tracksNew) { index, track ->
            val round: Dp? = if (index % 4 == 0) null else 10.dp
            CardRow(60.dp, round = round, roundPercent = 100, item = track)
        }
        item {
            TextTitle("Early", modifier = Modifier.padding(8.dp))
        }
        items(tracks) { track ->
            CardRow(60.dp, round = 10.dp, item = track)
        }

    }


}

@Composable
@Preview
fun NotificationsScreenPreview() {
    NotificationsScreen()
}