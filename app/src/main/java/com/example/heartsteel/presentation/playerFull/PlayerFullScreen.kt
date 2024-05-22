package com.example.heartsteel.presentation.playerFull

import android.annotation.SuppressLint
import android.util.Log
import android.widget.FrameLayout
import androidx.annotation.OptIn
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import com.example.heartsteel.R
import com.example.heartsteel.components.*
import com.example.heartsteel.components.core.TopBar
import com.example.heartsteel.navigation.Router
import com.example.heartsteel.tools.Ext
import com.example.heartsteel.tools.Ext.gradient
import com.example.heartsteel.tools.Ext.round
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.navigation.NavHostController
import com.example.heartsteel.domain.model.Music
import com.example.heartsteel.navigation.Screen
import com.example.heartsteel.tools.Ext.color
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

@SuppressLint("MutableCollectionMutableState")
@OptIn(UnstableApi::class)
@Composable
fun PlayerFullScreen(router: Router? = null, idMusic: String?, navController: NavHostController?) {
    val goBack: () -> Unit = {
        router?.goHome()
    }
    var titleMusic by remember { mutableStateOf("") }
    var authorMusic by remember { mutableStateOf("") }
    var lyricMusic by remember { mutableStateOf("") }
    var genreMusic by remember { mutableStateOf("") }
    var videoUrl by remember { mutableStateOf<String?>(null) }
    var tagMusic by remember { mutableStateOf("") }
    val listMusic = remember { mutableListOf<String?>(null) }
    val coroutineScope = rememberCoroutineScope()

    if (idMusic != null) {
        LaunchedEffect(idMusic) {
            coroutineScope.launch(Dispatchers.IO) {
                try {
                    val snapshot =
                        FirebaseDatabase.getInstance().getReference("musics")
                            .child(idMusic.toString()).get().await()
                    titleMusic = snapshot.child("title").value.toString()
                    authorMusic = snapshot.child("author").value.toString()
                    lyricMusic = snapshot.child("lyrics").value.toString()
                    videoUrl = snapshot.child("audio").value.toString()
                    genreMusic = snapshot.child("genre").value.toString()
                    tagMusic = snapshot.child("tag").value.toString()
                } catch (e: Exception) {
                    Log.e("NotificationsScreen", "Error fetching musics", e)
                }
            }
        }
    }
    fun selectRandomSong(): String? {
        if (listMusic.isEmpty() || listMusic.size == 1) {
            return null
        }

        var randomSong: String?
        do {
            randomSong = listMusic.random()
        } while (randomSong == idMusic)

        return randomSong
    }
    fun onSkipForward() {
        val nextSong = selectRandomSong()
        if (nextSong != null) {
            navController?.navigate("${Screen.PlayerFull.route}/${nextSong}")
        } else {
            println("No next song available.")
        }
    }

    LaunchedEffect(idMusic) {
        coroutineScope.launch(Dispatchers.IO) {
            try {
                val snapshot =
                    FirebaseDatabase.getInstance().getReference("musics").get().await()
                snapshot.children.forEach { dataSnap ->
                     Music().apply {
                        id = dataSnap.key!!
                        listMusic.add(id)
                    }

                }
            } catch (e: Exception) {
                Log.e("NotificationsScreen", "Error fetching musics", e)
            }
        }
    }
    val context = LocalContext.current
    if (videoUrl != null) {
        val exoPlayer = remember {
            ExoPlayer.Builder(context).build().apply {
                setMediaItem(MediaItem.fromUri(videoUrl!!))
            }
        }

        val playWhenReady = rememberSaveable { mutableStateOf(true) }
        val styleState = remember {
            mutableStateOf(true)
        }
        val isGridStyle = playWhenReady.value
        LaunchedEffect(key1 = idMusic) {
            exoPlayer.prepare()
            exoPlayer.playWhenReady = playWhenReady.value
        }
        DisposableEffect(key1 = idMusic) {
            onDispose {
                exoPlayer.release()
            }
        }
        //var isPlaying = true
        fun handlePause() {
            exoPlayer.playWhenReady = !exoPlayer.playWhenReady
            playWhenReady.value = !playWhenReady.value
        }
        fun handleStart() {
            exoPlayer.playWhenReady = !exoPlayer.playWhenReady
            playWhenReady.value = !playWhenReady.value
        }
        Column(
            Modifier
                .fillMaxSize()
                .gradient(
                    listOf(Color.Red, Color.Transparent, Color.Transparent),
                    Ext.GradientType.VERTICAL
                )
        ) {
            TopBar(
                navigationIcon = {
                    IconBtn(resIcon = R.drawable.ic_down, onClick = {goBack()})
                },
                title = {
                    Column(
                        modifier = Modifier.weight(2f),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = tagMusic, color = Color.White, onTextLayout = {})
                        TextTitle(text = authorMusic, fontSize = 18.sp)
                    }
                },
                actions = {
                    IconBtn(resIcon = R.drawable.ic_dots)
                }
            )
            Column(Modifier.padding(horizontal = 30.dp)) {
                Box(
                    modifier = Modifier
                        .size(340.dp)
                        .aspectRatio(16f / 9)
                ) {
                        AndroidView(factory = {
                            PlayerView(context).apply {
                                player = exoPlayer
                                layoutParams = FrameLayout.LayoutParams(
                                    FrameLayout.LayoutParams.MATCH_PARENT,
                                    FrameLayout.LayoutParams.MATCH_PARENT
                                )
                            }
                        })
                }
                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
                    IconBtn(resIcon = R.drawable.ic_h_outline)
                    IconBtn(
                        resIcon = R.drawable.ic_player_back,
                        modifier = Modifier.weight(1f),
                        onClick = { onSkipForward()}
                    )
                    Box(
                        modifier = Modifier
                            .size(65.dp)
                            .round(100)
                            .color(Color.White),
                        contentAlignment = Alignment.Center
                    ) {
                        if (isGridStyle){
                        IconBtn(resIcon = R.drawable.ic_player_pause, tint = Color.Black, onClick = {handlePause()})
                    } else {
                        IconBtn(resIcon = R.drawable.ic_player_play, tint = Color.Black, onClick = {handleStart() })
                    }
                    }
                    IconBtn(
                        resIcon = R.drawable.ic_player_skip,
                        modifier = Modifier.weight(1f),
                        onClick = { onSkipForward() },
                    )
                    IconBtn(resIcon = R.drawable.ic_remove)
                }
                TextTitle(text = titleMusic)
                TextSubtitle(text = genreMusic)
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp)
                ) {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 20.dp, bottom = 80.dp)
                            .round(8.dp)
                            .background(Color.Red)
                            .padding(20.dp),
                    ) {
                        item{
                            Text(
                                text = "Lyrics",
                                fontWeight = FontWeight.Bold,
                                fontSize = 15.sp,
                                color = Color.White,
                                onTextLayout = {}
                            )
                        }
                        item{
                            Text(
                                text = lyricMusic,
                                fontWeight = FontWeight.Bold,
                                fontSize = 24.sp,
                                maxLines = 100,
                                modifier = Modifier.padding(vertical = 20.dp),
                                onTextLayout = {}
                            )
                        }
                        item {
                            Surface(
                                shape = CircleShape,
                                modifier = Modifier
                                    .padding(vertical = 10.dp)
                                    .align(Alignment.End),
                                contentColor = Color.Transparent,
                                border = BorderStroke(
                                    width = 1.dp,
                                    color = Color.LightGray
                                ),
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.padding(horizontal = 30.dp, vertical = 4.dp)
                                ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.ic_baseline_share_24),
                                        contentDescription = null,
                                        tint = Color.White,
                                        modifier = Modifier.size(12.dp)
                                    )
                                    Text(
                                        text = "SHARE",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 11.sp,
                                        color = Color.White,
                                        modifier = Modifier.padding(horizontal = 8.dp),
                                        onTextLayout = {}
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
/*
@Composable
@Preview
fun PlayerFullScreenPreview() {
    PlayerFullScreen(idMusic = String())
}*/