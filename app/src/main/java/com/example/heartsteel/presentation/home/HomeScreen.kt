package com.example.heartsteel.presentation.home

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.heartsteel.components.*
import com.example.heartsteel.components.core.VerticalGrid
import com.example.heartsteel.domain.model.Music
import com.example.heartsteel.domain.model.Albums
import com.example.heartsteel.navigation.Router
import com.example.heartsteel.navigation.Screen
import com.example.heartsteel.ui.theme.Sizes
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

@ExperimentalFoundationApi
@Composable
fun HomeScreen(paddingValues: PaddingValues = PaddingValues(), router: Router? = null,navController: NavHostController?) {
    /*val categories = remember {
        DataProvider.categoriesBy()
    }*/
    val albums = remember { mutableListOf<Albums>()}
    val musicsNew = remember { mutableListOf<Music>()}
    val coroutineScope = rememberCoroutineScope()
    LaunchedEffect(Unit) {
        try {
            val snapshot = FirebaseDatabase.getInstance().getReference("albums").get().await()
            snapshot.children.forEach { dataSnap ->
                val album = Albums()
                album.id = dataSnap.key!!
                album.title = dataSnap.child("title").value.toString()
                album.subtitle = dataSnap.child("subtitle").value.toString()
                album.author = dataSnap.child("author").value.toString()
                album.imageRes = dataSnap.child("image").value.toString()

                val a = dataSnap.child("tracks")
                if (a.exists()) {
                    val trackList = mutableListOf<Music>()
                    a.children.forEach { trackDataSnap ->
                        val music = Music().apply {
                            id = trackDataSnap.key!!
                            title = trackDataSnap.child("title").value.toString()
                            audio = trackDataSnap.child("audio").value.toString()
                            lyrics = trackDataSnap.child("lyrics").value.toString()
                            image = trackDataSnap.child("image").value.toString()
                            likes = trackDataSnap.child("likes").value.toString()
                            tag = trackDataSnap.child("tag").value.toString()
                            author = trackDataSnap.child("author").value.toString()
                            genre = trackDataSnap.child("genre").value.toString()
                        }
                        trackList.add(music)
                    }
                    album.data = trackList
                }
                albums.add(album)
            }
        } catch (e: Exception) {
            Log.e("NotificationsScreen", "Error fetching tabs", e)
        }
    }
    LaunchedEffect(Unit){
        coroutineScope.launch(Dispatchers.IO) {
            try {
                val snapshot = FirebaseDatabase.getInstance().getReference("musics").limitToFirst(6).get().await()
                snapshot.children.forEach { dataSnap ->
                    val music = Music().apply {
                        id = dataSnap.key!!
                        title = dataSnap.child("title").value.toString()
                        audio = dataSnap.child("audio").value.toString()
                        lyrics = dataSnap.child("lyrics").value.toString()
                        image = dataSnap.child("image").value.toString()
                        likes = dataSnap.child("likes").value.toString()
                        tag = dataSnap.child("tag").value.toString()
                        author = dataSnap.child("author").value.toString()
                        genre =dataSnap.child("genre").value.toString()
                    }
                    musicsNew.add(music)
                }
            } catch (e: Exception) {
                Log.e("NotificationsScreen", "Error fetching tabs", e)
            }
        }
    }
    val goDetails: (Albums?) -> Unit = {
        navController?.navigate("${Screen.HomeDetails.route}/${it?.id}")
    }
    val goPlayer: (Music?) -> Unit = {
        navController?.navigate("${Screen.PlayerFull.route}/${it?.id}")
    }

    val actionClicked: (index: Int) -> Unit = {
        when (it) {
            0 -> router?.goNotification()
            1 -> router?.goHistory()
            2 -> router?.goSettings()
        }
    }


    LazyColumn(
        modifier = Modifier.padding(top = Sizes.MEDIUM),
        contentPadding = PaddingValues(bottom = paddingValues.calculateBottomPadding())
    ) {
        item {
            TopAppBar(onActionClicked = actionClicked)
        }

        item {
            VerticalGrid(
                modifier = Modifier.padding(horizontal = Sizes.SMALL)
            ) {
                musicsNew.forEach {
                    it.title?.let { it1 ->
                        SmallCardItem(
                            image = it.image,
                            title = it1
                        ) {
                            goPlayer(it)
                        }
                    }
                }
            }
        }

        itemsIndexed(albums) { index,album  ->
            val title = album.title
            val data = album.data
            var round: Dp? = 0.dp
            val roundPercent = 100

            if (index == 3 || index == 4 || index == 8) {
                round = null
            } else if (index > 16) {
                round = 8.dp
            }

            Column(
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Top
            ) {
                    if (title != null) {
                        TextTitle(title, modifier = Modifier.padding(horizontal = 8.dp))
                    }
                LazyRow {
                    items(data) { music ->
                        CardColumn(
                            roundPercent = roundPercent,
                            round = round,
                            onClick = { goDetails(album) },
                            item = music
                        )
                    }
                }
            }
        }
    }
}
/*
@ExperimentalFoundationApi
@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreen(navController = NavHostController())
}*/