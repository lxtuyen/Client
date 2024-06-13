package com.example.heartsteel.presentation.searchTag

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.heartsteel.R
import com.example.heartsteel.components.ChipTag
import com.example.heartsteel.components.IconBtn
import com.example.heartsteel.components.TextSubtitle
import com.example.heartsteel.components.TextTitle
import com.example.heartsteel.components.core.BaseRow
import com.example.heartsteel.components.core.TopBar
import com.example.heartsteel.domain.model.Music
import com.example.heartsteel.domain.model.Tabs
import com.example.heartsteel.navigation.Router
import com.example.heartsteel.navigation.Screen
import com.example.heartsteel.tools.Ext.clickableResize
import com.example.heartsteel.ui.theme.Sizes
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

@SuppressLint("RememberReturnType")
@Composable
fun SearchTagScreen(
    router: Router? = null,
    tagName: String?,
    paddingValues: PaddingValues = PaddingValues(),
    navController: NavHostController?
) {
    val contentHeight = 150.dp
    val scrollState = rememberLazyListState()

    val tabsList = remember { mutableListOf<Tabs>() }
    val listMusic = remember { mutableListOf<Music>() }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        coroutineScope.launch(Dispatchers.IO) {
            try {
                val snapshot = FirebaseDatabase.getInstance().getReference("tabs").get().await()

                snapshot.children.forEach { dataSnap ->
                    val tab = dataSnap.getValue(Tabs::class.java)
                    tab?.let { tabsList.add(it) }
                }
            } catch (e: Exception) {
                Log.e("NotificationsScreen", "Error fetching tabs", e)
            }
        }
    }
    LaunchedEffect(Unit) {
        coroutineScope.launch(Dispatchers.IO) {
            try {
                val databaseReference = FirebaseDatabase.getInstance().getReference("musics")
                val snapshot = databaseReference.get().await()

                snapshot.children.forEach { dataSnap ->
                    if(dataSnap.child("tag").value.toString() == tagName){
                    val music = Music().apply {
                        id = dataSnap.key!!
                        title = dataSnap.child("title").value.toString()
                        image = dataSnap.child("image").value.toString()
                        tag = dataSnap.child("tag").value.toString()
                    }
                    listMusic.add(music)}
                }
            } catch (e: Exception) {
                Log.e("NotificationsScreen", "Error fetching musics", e)
            }
        }
    }
    val goBack: () -> Unit = {
        router?.goHome()
    }
    val goPlayer: (Music?) -> Unit = {
        navController?.navigate("${Screen.PlayerFull.route}/${it?.id}")
    }
    val goSearchTag: (Tabs?) -> Unit = {
        navController?.navigate("${Screen.SearchTag.route}/${it?.title}")
    }

    TopBar(
        modifier = Modifier,
        navigationIcon = {
            IconBtn(
                resIcon = R.drawable.ic_left,
                onClick = { goBack() }
            )
        },
    )

    LazyColumn(
        modifier = Modifier.padding(start = 10.dp, end = 10.dp),
        state = scrollState,
        contentPadding = PaddingValues(
            bottom = paddingValues.calculateBottomPadding(),
        )
    ) {
        item {
            Box(modifier = Modifier.height(contentHeight)) {
                LazyRow(
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .align(Alignment.BottomStart),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    items(tabsList) { tab ->
                        tab.title?.let {
                            ChipTag(modifier = Modifier.padding(end = 8.dp), text = it, onChipSelected = {goSearchTag(tab)})
                        }
                    }
                }
            }
        }
        items(listMusic) {
            BaseRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = Sizes.MEDIUM)
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
                    text = it.tag ?: "tag",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Light,
                    color = Color.Gray
                )
            }
        }
    }
}