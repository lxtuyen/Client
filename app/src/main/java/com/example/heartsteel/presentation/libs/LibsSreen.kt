package com.example.heartsteel.presentation.libs

import android.util.Log
import android.widget.Toast
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.heartsteel.R
import com.example.heartsteel.components.*
import com.example.heartsteel.components.core.TopBar
import com.example.heartsteel.components.core.VerticalGrid
import com.example.heartsteel.navigation.Router
import com.example.heartsteel.tools.Ext.clickableResize
import com.example.heartsteel.tools.Ext.color
import com.example.heartsteel.tools.Ext.round
import com.example.heartsteel.ui.theme.Sizes
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import com.example.heartsteel.components.core.BaseRow
import com.example.heartsteel.domain.model.Music
import com.example.heartsteel.domain.model.Tabs
import com.example.heartsteel.navigation.Screen
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

@ExperimentalMaterial3Api
@Composable
fun LibsScreen(
    paddingValues: PaddingValues = PaddingValues(),
    router: Router? = null,
    navController: NavHostController?
) {
    val goAddPersons: () -> Unit = {
        router?.goAddPersons()
    }

    val context = LocalContext.current

    val chipState = remember {
        mutableIntStateOf(-1)
    }
    val chipSelected = chipState.intValue != -1
    val tempList =remember { mutableListOf<Tabs>()}

    var isLoading by remember { mutableStateOf(false) }

    val listMusic = remember { mutableListOf<Music>()}
    val coroutineScope = rememberCoroutineScope()
    val userId = Firebase.auth.currentUser?.uid
    LaunchedEffect(Unit){
        coroutineScope.launch(Dispatchers.IO) {
            try {
                val snapshot = FirebaseDatabase.getInstance().getReference("users").child(userId.toString())
                    .child("album").get().await()

                snapshot.children.forEach { dataSnap ->
                    val music = Music().apply {
                        id = dataSnap.key!!
                        title = dataSnap.child("title").value.toString()
                        image = dataSnap.child("image").value.toString()
                        genre = dataSnap.child("genre").value.toString()
                        author  = dataSnap.child("author").value.toString()
                    }
                    listMusic.add(music)
                }
                isLoading = true
            } catch (e: Exception) {
                Log.e("NotificationsScreen", "Error fetching tabs", e)
            }
        }
    }

    LaunchedEffect(Unit) {
        try {
            val snapshot = FirebaseDatabase.getInstance().getReference("tabs").get().await()

            snapshot.children.forEach { dataSnap ->
                val tab = dataSnap.getValue(Tabs::class.java)
                tab?.let { tempList.add(it) }
            }
            Log.d("NotificationsScreen", "Tabs fetched successfully: $tempList")
        } catch (e: Exception) {
            Log.e("NotificationsScreen", "Error fetching tabs", e)
        }
    }

    val styleState = remember {
        mutableStateOf(true)
    }

    val isGridStyle = styleState.value

    val deleteMusic: (Music?) -> Unit = { music ->
        if (userId != null && music != null) {
            FirebaseDatabase.getInstance().getReference("users").child(userId.toString())
                .child("album").child(music.id.toString()).removeValue()
                .addOnSuccessListener {
                    Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show()
                    router?.goLib()
                }
                .addOnFailureListener { exception ->
                    Log.e("AddMusic", "Error adding music to database", exception)
                }
        }
    }
    val goPlayer: (Music?) -> Unit = {
        navController?.navigate("${Screen.PlayerFull.route}/${it?.id}")
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
                IconBtn(resIcon = R.drawable.ic_search_small, onClick = goAddPersons)
                IconBtn(resIcon = R.drawable.ic_baseline_add_24, onClick = goAddPersons)
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
                            val selectedChip = tempList[chipState.intValue]
                            selectedChip.let {
                                it.title?.let { it1 ->
                                    ChipTag(selected = true, text = it1) {
                                        chipState.intValue = -1
                                    }
                                }
                            }
                        }
                    } else {
                        itemsIndexed(tempList) { index, tab   ->
                            tab.title?.let {
                                ChipTag(modifier = Modifier.padding(end = 8.dp), text = it) {
                                    chipState.intValue = index
                                }
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

                    IconBtn(resIcon = R.drawable.ic_baseline_arrow_downward_24)
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
                item {
                    GridContent(items = listMusic, goAddPersons,navController)
                }
            } else {
                itemsIndexed(listMusic) { index, music ->
                    val round: Dp? = if (index % 4 == 0) null else 10.dp
                    BaseRow(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = Sizes.MEDIUM)
                            .clickableResize {goPlayer(music)},
                        imageSize = 50.dp,
                        imageRes = music.image,
                        round = round,
                        roundPercent = 100,
                        contentEnd = {
                            IconBtn(
                                resIcon = R.drawable.ic_delete_black,
                                tint = Color.Gray,
                                onClick = { deleteMusic(music) }
                            )
                        }
                    ) {
                        TextTitle(
                            text = music.title ?: "title",
                            fontSize = 22.sp
                        )
                        TextSubtitle(
                            text = music.author ?: "author",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Light,
                            color = Color.Gray
                        )
                    }
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
                            text = "Add Music",
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
        }
    }
}
@Composable
fun GridContent(items: List<Music>, onClickAdd: () -> Unit,navController: NavHostController? ) {
    val goPlayer: (Music?) -> Unit = {
        navController?.navigate("${Screen.PlayerFull.route}/${it?.id}")
    }
    VerticalGrid {
        items.forEachIndexed { index, music ->
            val round: Dp? = if (index % 4 == 0) null else 10.dp
            CardColumn(145.dp, round = round, roundPercent = 100, item = music, onClick = {goPlayer(music)})
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
                text = "Add Music",
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
/*
@ExperimentalComposeUiApi
@ExperimentalMaterial3Api
@Composable
@Preview
fun LibsScreenPreview() {
    LibsScreen()
}*/