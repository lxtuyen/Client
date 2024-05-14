package com.example.heartsteel.presentation.addPersons

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.heartsteel.R
import com.example.heartsteel.components.IconBtn
import com.example.heartsteel.components.SearchBar
import com.example.heartsteel.components.TextSubtitle
import com.example.heartsteel.components.TextTitle
import com.example.heartsteel.components.core.BaseRow
import com.example.heartsteel.domain.model.Music
import com.example.heartsteel.navigation.Router
import com.example.heartsteel.tools.Ext
import com.example.heartsteel.tools.Ext.color
import com.example.heartsteel.tools.Ext.gradient
import com.example.heartsteel.tools.Ext.offsetY
import com.example.heartsteel.tools.Ext.round
import com.example.heartsteel.ui.theme.Sizes
import com.example.heartsteel.ui.theme.Primary30
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

@ExperimentalFoundationApi
@Composable
fun AddPersonsScreen(router: Router? = null) {
    val (value, setValue) = rememberSaveable {
        mutableStateOf("")
    }
    val context = LocalContext.current
    var isLoading by remember { mutableStateOf(false) }
    val listMusic = remember { mutableListOf<Music>() }
    val coroutineScope = rememberCoroutineScope()
    val userId = Firebase.auth.currentUser?.uid
    LaunchedEffect(Unit) {
        coroutineScope.launch(Dispatchers.IO) {
            try {
                val snapshot =
                    FirebaseDatabase.getInstance().getReference("musics").limitToFirst(10).get()
                        .await()

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
                Log.e("AddPersonsScreen", "Error fetching music", e)
            }
        }
    }
    val addMusic: (Music?) -> Unit = { music ->
        if (userId != null && music != null) {
            val albumRef = FirebaseDatabase.getInstance().getReference("users").child(userId.toString()).child("album")
            val musicQuery = albumRef.orderByChild("id").equalTo(music.id)

            musicQuery.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if (!dataSnapshot.exists()) {
                        albumRef.push().setValue(music)
                            .addOnSuccessListener {
                                Toast.makeText(context, "Thêm thành công", Toast.LENGTH_SHORT).show()
                                router?.goLib()
                            }
                            .addOnFailureListener { exception ->
                                Log.e("AddPersonsScreen", "Error adding music to database", exception)
                            }
                    } else{
                        Toast.makeText(context, "Nhạc đã thêm vào thư viện", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    Log.e("AddPersonsScreen", "Error checking music existence", databaseError.toException())
                }
            })
        }
    }

    val scrollState = rememberLazyListState()
    val contentHeight = 100.dp
    val offsetY = scrollState.offsetY(contentHeight)
    if (isLoading) {
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
                        text = "Search Musics",
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
                columns = GridCells.Fixed(1)
            ) {
                items(listMusic) {
                    BaseRow(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = Sizes.MEDIUM),
                        imageSize = 50.dp,
                        imageRes = it.image,
                        roundPercent = 100,
                        contentEnd = {
                            IconBtn(
                                resIcon = R.drawable.ic_h_outline,
                                tint = Color.Gray,
                                onClick = { addMusic(it) }
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
}

@ExperimentalFoundationApi
@Composable
@Preview
fun AddPersonsScreenPreview() {
    AddPersonsScreen()
}