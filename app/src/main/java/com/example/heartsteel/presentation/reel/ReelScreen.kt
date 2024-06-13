package com.example.heartsteel.presentation.reel

import android.annotation.SuppressLint
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.heartsteel.domain.model.Reels
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

val verticalPadding = 12.dp
val horizontalPadding = 10.dp

@Composable
fun ReelScreen() {
    Box(Modifier.background(color = Color.Black)) {
        ReelsList()

        ReelsHeader()
    }
}

@Composable
fun ReelsHeader() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = horizontalPadding,
                vertical = verticalPadding
            ),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text("Reels", color = Color.White, fontWeight = FontWeight.SemiBold, fontSize = 21.sp)
    }
}

@OptIn(ExperimentalFoundationApi::class)
@SuppressLint("MutableCollectionMutableState")
@Composable
fun ReelsList() {
    val coroutineScope = rememberCoroutineScope()
    var reelList by remember { mutableStateOf<List<Reels>>(emptyList()) }

    val playedVideos by remember { mutableStateOf(HashSet<String>()) }

    LaunchedEffect(Unit) {
        coroutineScope.launch(Dispatchers.IO) {
            try {
                val fetchedReel = mutableListOf<Reels>()
                val snapshot = FirebaseDatabase.getInstance().getReference("reels").limitToLast(2).get().await()
                snapshot.children.forEach { dataSnap ->

                    val reel = Reels().apply {
                        id = dataSnap.key!!
                        caption = dataSnap.child("caption").value.toString()
                        url = dataSnap.child("video").value.toString()
                        author = dataSnap.child("author").value.toString()
                    }
                    fetchedReel.add(reel)
                }
                reelList = fetchedReel
            } catch (e: Exception) {
                Log.e("ReelsList", "Error fetching reels", e)
            }
        }
    }
    val pageState = rememberPagerState(pageCount = { reelList.size })
    VerticalPager(state = pageState,key={reelList[it]}, pageSize = PageSize.Fill) { index ->
        val reel = reelList[index]
        val isPlaying = !playedVideos.contains(reel.url)
        Box(Modifier.fillMaxSize()) {
        VideoPlayer(uri = Uri.parse(reel.url), isPlaying = isPlaying) {
            if (isPlaying) {
                playedVideos.add(reel.url!!)
            }
        }
            Column(Modifier.align(Alignment.BottomStart)) {
                ReelFooter(reel)
                Divider(modifier = Modifier.fillMaxWidth())
            }
    }}
}

@Composable
fun ReelFooter(reel: Reels) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(bottom = 40.dp), verticalAlignment = Alignment.Bottom
    ) {
        FooterUserData(
            reel = reel,
            modifier = Modifier.weight(8f)
        )
    }
}

@Composable
fun FooterUserData(reel: Reels, modifier: Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center
    ) {

        reel.author?.let {
            Text(
                text = it,
                color = Color.White,
                style = MaterialTheme.typography.titleLarge
            )
        }
        Spacer(modifier = Modifier.height(horizontalPadding))
        Text(text = reel.caption.toString(), color = Color.White)
        Spacer(modifier = Modifier.height(horizontalPadding))
    }
}