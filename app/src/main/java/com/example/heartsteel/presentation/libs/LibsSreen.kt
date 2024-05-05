package com.example.heartsteel.presentation.libs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.heartsteel.R
import com.example.heartsteel.components.*
import com.example.heartsteel.components.core.TopBar
import com.example.heartsteel.components.core.VerticalGrid
import com.example.heartsteel.domain.model.Music
import com.example.heartsteel.navigation.Router
import com.example.heartsteel.repository.DataProvider
import com.example.heartsteel.tools.Ext.clickableResize
import com.example.heartsteel.tools.Ext.color
import com.example.heartsteel.tools.Ext.round
import com.example.heartsteel.ui.theme.Sizes
import com.example.heartsteel.ui.theme.Active
import com.example.heartsteel.ui.theme.Primary80
import com.example.heartsteel.ui.theme.Secondary
import com.example.heartsteel.ui.theme.White
import androidx.compose.material3.ModalBottomSheet

@ExperimentalMaterial3Api
@Composable
fun LibsScreen(
    paddingValues: PaddingValues = PaddingValues(),
    router: Router? = null
) {
    val goAddPersons: () -> Unit = {
        router?.goAddPersons()
    }
    val goAddPodcasts: () -> Unit = {
        router?.goAddPodcasts()
    }

    val tracks = remember {
        DataProvider.itemsBy(4, 20)
    }
    val chipState = remember {
        mutableIntStateOf(-1)
    }
    val chipSelected = chipState.intValue != -1
    val chips = remember { DataProvider.tags() }

    val styleState = remember {
        mutableStateOf(true)
    }
    val isModalBottomSheetVisible = remember { mutableStateOf(false) }

    val isGridStyle = styleState.value

    val showFilter: () -> Unit = {
        isModalBottomSheetVisible.value = !isModalBottomSheetVisible.value
    }

    Column {
        TopBar(
            navigationIcon = {
                Box(
                    modifier = Modifier
                        .padding(horizontal = 12.dp, vertical = 8.dp)
                        .size(35.dp)
                        .round(100)
                        .color(Color.Blue)
                ) {
                    TextTitle(
                        text = "V",
                        modifier = Modifier.align(Alignment.Center),
                        fontSize = 13.sp
                    )
                }

            },
            title = {
                TextTitle(
                    modifier = Modifier
                        .weight(3f)
                        .padding(10.dp),
                    text = "Your Library"
                )
            },
            actions = {
                IconBtn(resIcon = R.drawable.ic_search_small)
                IconBtn(resIcon = R.drawable.ic_baseline_add_24)
            }
        )
        LazyColumn(
            contentPadding = PaddingValues(
                bottom = paddingValues.calculateBottomPadding(),
                start = Sizes.MEDIUM,
                end = Sizes.MEDIUM
            )
        ) {
            item {
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 8.dp),
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

            item {
                Row(
                    modifier = Modifier.padding(top = 10.dp, bottom = 10.dp, end = 10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    IconBtn(resIcon = R.drawable.ic_baseline_arrow_downward_24) {
                        showFilter()
                    }
                    Text(
                        text = "Recently played",
                        fontSize = 12.sp,
                        color = Color.White,
                        modifier = Modifier.weight(2f),
                        onTextLayout = {}
                    )

                    IconBtn(
                        resIcon = R.drawable.ic_baseline_list_24,
                        selectedIcon = R.drawable.ic_baseline_grid_view_24,
                        selected = isGridStyle
                    ) {
                        styleState.value = !styleState.value
                    }
                }
            }

            if (!isGridStyle) {
                itemsIndexed(tracks) { index, person ->
                    val round: Dp? = if (index % 4 == 0) null else 10.dp
                    CardRow(60.dp, round = round, roundPercent = 100, item = person){ showFilter() }
                }
                item {
                    Row(
                        modifier = Modifier
                            .padding(Sizes.MEDIUM)
                            .clickableResize(goAddPersons),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        ImageCrop(
                            modifier = Modifier
                                .round(100)
                                .size(60.dp)
                                .background(Color(0x33868686)),
                            data = R.drawable.ic_baseline_add_24,
                        )
                        Text(
                            text = "Add artists",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(
                                top = Sizes.MEDIUM,
                                start = Sizes.MEDIUM
                            ),
                            onTextLayout = {}
                        )
                    }
                }
                item {
                    Row(
                        modifier = Modifier
                            .padding(Sizes.MEDIUM)
                            .clickableResize(goAddPodcasts),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        ImageCrop(
                            modifier = Modifier
                                .size(60.dp)
                                .round(10.dp)
                                .background(Color(0x33868686)),
                            data = R.drawable.ic_baseline_add_24,
                        )
                        Text(
                            text = "Add albums",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(
                                top = Sizes.MEDIUM,
                                start = Sizes.MEDIUM
                            ),
                            onTextLayout = {}
                        )
                    }
                }
            } else {
                item {
                    GridContent(items = tracks, goAddPersons, showFilter)
                }
            }
        }
    }
    if (isModalBottomSheetVisible.value) {
        ModalBottomSheet(
            onDismissRequest = { showFilter() },
            modifier = Modifier.fillMaxWidth(),
            containerColor = Secondary,
            scrimColor = Primary80,
        ) {
            SheetContent()
        }
    }
}
@Composable
fun GridContent(items: List<Music>, onClickAdd: () -> Unit, onClickView: () -> Unit ) {
    VerticalGrid {
        items.forEachIndexed { index, music ->
            val round: Dp? = if (index % 4 == 0) null else 10.dp
            CardColumn(145.dp, round = round, roundPercent = 100, item = music){ onClickView() }
        }
        Column(
            modifier = Modifier
                .clickableResize {
                    onClickAdd()
                },
            horizontalAlignment = CenterHorizontally
        ) {
            ImageCrop(
                modifier = Modifier
                    .size(145.dp)
                    .round(100)
                    .background(Color(0x33868686)),
                data = R.drawable.ic_baseline_add_24,
            )
            Text(
                text = "Add artist",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(
                    top = Sizes.MEDIUM,
                    start = Sizes.MEDIUM
                ),
                onTextLayout = {}
            )
        }
        Column(
            modifier = Modifier
                .clickableResize {
                    onClickAdd()
                },
            horizontalAlignment = CenterHorizontally
        ) {
            ImageCrop(
                modifier = Modifier
                    .size(145.dp)
                    .round(10.dp)
                    .background(Color(0x33868686)),
                data = R.drawable.ic_baseline_add_24,
            )
            Text(
                text = "Add album",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(
                    top = Sizes.MEDIUM,
                    start = Sizes.MEDIUM
                ),
                onTextLayout = {}
            )
        }
    }

}

@Composable
fun SheetContent() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, end = 20.dp, top = 20.dp, bottom = 250.dp)
    ) {
        Box(
            modifier = Modifier
                .height(4.dp)
                .width(30.dp)
                .round(2.dp)
                .align(CenterHorizontally)
                .color(Color.Gray)
        )
        TextTitle()

        LazyColumn {

            item {
                Row {
                    Text(
                        "Item ",
                        color = Active,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .padding(10.dp)
                            .weight(1f),
                        onTextLayout = {}
                    )
                    Icon(
                        painter = painterResource(id = R.drawable.ic_baseline_check_24),
                        contentDescription = null,
                        tint = Active
                    )
                }
            }
            items(3) {
                Text(
                    "Item $it",
                    color = White,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(10.dp),
                    onTextLayout = {}
                )
            }
        }
        TextTitle(
            text = "cancel",
            color = Color.Gray,
            modifier = Modifier.align(CenterHorizontally)
        )
    }
}

@ExperimentalComposeUiApi
@ExperimentalMaterial3Api
@Composable
@Preview
fun LibsScreenPreview() {
    LibsScreen()
}