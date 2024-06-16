package com.example.heartsteel.presentation.search

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.heartsteel.components.IconBtn
import com.example.heartsteel.components.SearchBar
import com.example.heartsteel.components.TextTitle
import com.example.heartsteel.components.core.BaseRow
import com.example.heartsteel.domain.model.Music
import com.example.heartsteel.tools.Ext.offsetY
import com.example.heartsteel.tools.Ext.clickableResize
import com.example.heartsteel.ui.theme.Sizes
import com.example.heartsteel.ui.theme.Sizes.MEDIUM
import com.example.heartsteel.R
import com.example.heartsteel.components.TextSubtitle
import com.example.heartsteel.navigation.Screen

@ExperimentalFoundationApi
@Composable
fun SearchScreen(
    paddingValues: PaddingValues = PaddingValues(),
    navController: NavHostController?
) {

    val viewModel: SearchViewModel = viewModel()
    val searchText by viewModel.searchText.collectAsState()
    val music by viewModel.movies.collectAsState()

    val scrollState = rememberLazyListState()
    val contentHeight = 100.dp
    val offsetY = scrollState.offsetY(contentHeight)

    val goPlayer: (Music?) -> Unit = {
        navController?.navigate("${Screen.PlayerFull.route}/${it?.id}")
    }
    LazyVerticalGrid(
        state = rememberLazyGridState(0),
        modifier = Modifier.padding(top = 70.dp, start = 10.dp),
        contentPadding = PaddingValues(
            top = 130.dp,
            bottom = paddingValues.calculateBottomPadding()
        ),
        columns = GridCells.Fixed(1)
    ) {
        items(music) {
            BaseRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = MEDIUM)
                    .clickableResize { goPlayer(it) },
                imageSize = 50.dp,
                imageRes = it.image,
                contentEnd = {
                    IconBtn(
                        resIcon = R.drawable.ic_dots,
                        tint = Color.Gray
                    )
                }
            ) {
                TextTitle(
                    text = it.title ?: "title",
                    fontSize = 22.sp
                )
                TextSubtitle(
                    text = it.author ?: "author",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Light,
                    color = Color.Gray
                )
            }
        }
    }
    Column(
        modifier = Modifier.offset(y = offsetY.coerceIn(0.dp, contentHeight))
    ) {
        Box(
            modifier = Modifier
                .height(contentHeight)
                .padding(horizontal = Sizes.MEDIUM)
                .fillMaxWidth(),
            contentAlignment = Alignment.BottomStart
        ) {
            TextTitle(text = "Tìm Kiếm")
        }
        SearchBar(
            modifier = Modifier
                .padding(Sizes.MEDIUM)
                .height(60.dp),
            onValueChange = { viewModel.onSearchTextChange(it) },
            value = searchText,
            placeholder = "Tìm Kiếm"
        )
    }
}
/*
@ExperimentalFoundationApi
@ExperimentalComposeUiApi
@Preview
@Composable
fun SearchScreenPreview() {
    SearchScreen()
}*/