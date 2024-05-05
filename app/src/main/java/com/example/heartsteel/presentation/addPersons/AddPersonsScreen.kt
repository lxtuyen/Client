package com.example.heartsteel.presentation.addPersons

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.heartsteel.components.CardColumn
import com.example.heartsteel.components.SearchBar
import com.example.heartsteel.components.TextTitle
import com.example.heartsteel.repository.DataProvider
import com.example.heartsteel.tools.Ext
import com.example.heartsteel.tools.Ext.color
import com.example.heartsteel.tools.Ext.gradient
import com.example.heartsteel.tools.Ext.offsetY
import com.example.heartsteel.tools.Ext.round
import com.example.heartsteel.ui.theme.Sizes
import com.example.heartsteel.ui.theme.Primary30

@ExperimentalFoundationApi
@Composable
fun AddPersonsScreen() {
    val (value,setValue) = rememberSaveable {
        mutableStateOf("")
    }
    val persons = remember {
        DataProvider.itemsBy(3, 20)
    }

    val scrollState = rememberLazyListState()
    val contentHeight = 100.dp
    val offsetY = scrollState.offsetY(contentHeight)

    Box {
        Column(
            modifier = Modifier.offset(y = -offsetY)
        ) {
            Box(
                modifier = Modifier
                    .height(contentHeight)
                    .fillMaxWidth(),
                contentAlignment = Alignment.BottomStart
            ) {

                TextTitle(
                    modifier = Modifier.padding(Sizes.DEFAULT),
                    text = "Search artists",
                )
            }
            SearchBar(
                modifier = Modifier
                    .padding(Sizes.MEDIUM)
                    .height(60.dp),
                onValueChange = setValue,
                value = value,
                placeholder = "search"
            )
        }

        LazyVerticalGrid(
            state = LazyGridState(0),
            modifier = Modifier.padding(top = 70.dp),
            contentPadding = PaddingValues(top = 130.dp, bottom = 80.dp),
            columns = GridCells.Fixed(3)
        ) {
            items(persons) {
                CardColumn(100.dp, roundPercent = 100, item = it)
            }
        }
        Box(
            modifier = Modifier
                .height(150.dp)
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .gradient(
                    listOf(Color.Transparent, Primary30),
                    Ext.GradientType.VERTICAL
                )
        ) {
            Text(
                modifier = Modifier
                    .width(100.dp)
                    .align(Alignment.BottomCenter)
                    .padding(vertical = 10.dp)
                    .round(100)
                    .color(Color.White)
                    .padding(vertical = 10.dp, horizontal = 20.dp),
                textAlign = TextAlign.Center,
                text = "title",
                fontWeight = FontWeight.Bold,
                onTextLayout = {}
            )
        }
    }
}

@ExperimentalFoundationApi
@Composable
@Preview
fun AddPersonsScreenPreview() {
    AddPersonsScreen()
}