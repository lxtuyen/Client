package com.example.heartsteel.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.heartsteel.components.SearchBar
import com.example.heartsteel.components.SearchGradientCard
import com.example.heartsteel.components.TextTitle
import com.example.heartsteel.repository.DataProvider
import com.example.heartsteel.tools.Ext.offsetY
import com.example.heartsteel.tools.Ext.clickableResize
import com.example.heartsteel.ui.theme.Sizes

@ExperimentalFoundationApi
@Composable
fun SearchScreen(paddingValues: PaddingValues = PaddingValues()) {
    val categories = remember {
        DataProvider.categoriesBy(14)
    }

    val scrollState = rememberLazyListState()
    val contentHeight = 100.dp
    val offsetY = scrollState.offsetY(contentHeight)

    LazyVerticalGrid(
        state = rememberLazyGridState(0),
        modifier = Modifier.padding(top = 70.dp),
        contentPadding = PaddingValues(
            top = 130.dp,
            bottom = paddingValues.calculateBottomPadding()
        ),
        columns = GridCells.Fixed(2)
    ) {
        items(categories) {
            it.title?.let { it1 ->
                SearchGradientCard(
                    it.imageRes,
                    it1
                )
            }
        }
    }
    Column(
        modifier = Modifier.offset(y = -offsetY)
    ) {
        Box(
            modifier = Modifier
                .height(contentHeight)
                .padding(horizontal = Sizes.MEDIUM)
                .fillMaxWidth(),
            contentAlignment = Alignment.BottomStart
        ) {
            TextTitle(text = "Search")
        }
        SearchBar(
            modifier = Modifier
                .padding(Sizes.MEDIUM)
                .height(50.dp)
                .clickableResize {

                },
            title = "Artists,songs,or podcasts"
        )
    }
}

@ExperimentalFoundationApi
@ExperimentalComposeUiApi
@Preview
@Composable
fun SearchScreenPreview() {
    SearchScreen()
}