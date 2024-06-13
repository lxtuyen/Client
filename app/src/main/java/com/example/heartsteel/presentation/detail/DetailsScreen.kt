package com.example.heartsteel.presentation.detail

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.heartsteel.components.*
import com.example.heartsteel.components.core.BaseRow
import com.example.heartsteel.components.core.TopBar
import com.example.heartsteel.components.core.VerticalGrid
import com.example.heartsteel.tools.Ext.clickableResize
import com.example.heartsteel.tools.Ext.color
import com.example.heartsteel.tools.Ext.offsetY
import com.example.heartsteel.tools.Ext.round
import com.example.heartsteel.ui.theme.Sizes
import com.example.heartsteel.ui.theme.Sizes.MEDIUM
import com.example.heartsteel.ui.theme.Active
import com.example.heartsteel.R
import com.example.heartsteel.domain.model.Music
import com.example.heartsteel.navigation.Screen
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

@Composable
fun DetailsScreen(
    paddingValues: PaddingValues = PaddingValues(),
    idAlbum: String?,
    navController: NavHostController?
) {
    var isLoading by remember { mutableStateOf(false) }
    var titleAlbum by remember { mutableStateOf("") }
    var authorAlbum by remember { mutableStateOf("") }
    var imageAlbum by remember { mutableStateOf("") }
    val dataOfAlbum = remember { mutableListOf<Music>()}
    val listMusic = remember { mutableListOf<Music>() }
    val coroutineScope = rememberCoroutineScope()
    LaunchedEffect(idAlbum) {
        coroutineScope.launch(Dispatchers.IO) {
            try {
                val snapshot =
                    FirebaseDatabase.getInstance().getReference("musics").limitToFirst(10).get()
                        .await()

                snapshot.children.forEach { dataSnap ->
                    val music = Music().apply {
                        title = dataSnap.child("title").value.toString()
                        image = dataSnap.child("image").value.toString()
                    }
                    listMusic.add(music)
                }
            } catch (e: Exception) {
                Log.e("NotificationsScreen", "Error fetching musics", e)
            }
        }
    }
    LaunchedEffect(idAlbum) {
        try {
            val snapshot =
                FirebaseDatabase.getInstance().getReference("albums").child(idAlbum.toString())
                    .get().await()
                titleAlbum = snapshot.child("title").value.toString()
                authorAlbum = snapshot.child("author").value.toString()
                imageAlbum = snapshot.child("image").value.toString()

                val a = snapshot.child("tracks")
                if (a.exists()) {
                    a.children.forEach { trackDataSnap ->
                        val music = Music().apply {
                            id = trackDataSnap.key!!
                            title = trackDataSnap.child("title").value.toString()
                            image = trackDataSnap.child("image").value.toString()
                            genre = trackDataSnap.child("genre").value.toString()
                        }
                        dataOfAlbum.add(music)
                    }
                }
            isLoading = true
        } catch (e: Exception) {
            Log.e("NotificationsScreen", "Error fetching tabs", e)
        }
    }
    val playPadding = 25.dp
    val contentHeight = 350.dp
    val scrollState = rememberLazyListState()
    val offsetY = scrollState.offsetY(contentHeight)
    val alpha = 1f - offsetY.value / contentHeight.value

    val offset = contentHeight - offsetY
    val playOffset = if (offset < playPadding) {
        playPadding
    } else {
        offset
    }
    val goPlayer: (Music?) -> Unit = {
        navController?.navigate("${Screen.PlayerFull.route}/${it?.id}")
    }
    LazyColumn(
        state = scrollState,
        modifier = Modifier.padding(
            top = 50.dp,
            start = 20.dp,
            end = 10.dp
        ),
        contentPadding = PaddingValues(
            bottom = paddingValues.calculateBottomPadding()
        )
    ) {
        item {
            Column(
                modifier = Modifier
                    .height(contentHeight)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.Bottom
            ) {
                ImageCrop(
                    modifier = Modifier
                        .size(230.dp)
                        .alpha(alpha)
                        .scale(alpha)
                        .align(Alignment.CenterHorizontally),
                    data = imageAlbum,
                    contentScale = ContentScale.FillBounds
                )
                TextTitle(text = titleAlbum)
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconBtn(resIcon = R.drawable.ic_h_outline)
                    IconBtn(resIcon = R.drawable.ic_dots, tint = Color.Gray)
                    Spacer(modifier = Modifier.weight(2f))
                }
            }
        }
        if (isLoading) {
            items(dataOfAlbum) {
                BaseRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = MEDIUM)
                        .clickableResize {goPlayer(it)},
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
                        text = it.genre ?: "genre",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Light,
                        color = Color.Gray
                    )
                }
            }
        }
        item {
            TextTitle(
                modifier = Modifier
                    .padding(Sizes.DEFAULT),
                text = "Title",
            )
            VerticalGrid(
                modifier = Modifier
                    .fillMaxWidth(),
            ) {
                listMusic.forEach {
                    CardColumn(roundPercent = 100, item = it)
                }
            }
        }
    }

    Box {
        TopBar(
            navigationIcon = {
                IconBtn(resIcon = R.drawable.ic_left)
            },
            title = {
                TextTitle(text = authorAlbum, fontSize = 18.sp)
            }
        )

        PlayBtn(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .offset(y = playOffset)
        )
    }
}

@Composable
fun PlayBtn(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.BottomEnd
    ) {
        IconBtn(
            modifier = Modifier
                .padding(end = 12.dp)
                .size(50.dp)
                .round(100)
                .color(Active),
            resIcon = R.drawable.ic_baseline_play_arrow_24,
            tint = Color.Black
        )

        Icon(
            painter = painterResource(id = R.drawable.ic_player_shuffle),
            contentDescription = null,
            tint = Active,
            modifier = Modifier
                .padding(end = 10.dp)
                .size(20.dp)
                .round(100)
                .color(Color.DarkGray)
                .padding(4.dp)
                .align(Alignment.BottomEnd)
        )
    }
}
