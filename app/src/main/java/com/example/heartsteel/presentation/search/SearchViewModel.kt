package com.example.heartsteel.presentation.search

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.heartsteel.domain.model.Music
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class SearchViewModel : ViewModel() {
    private val _searchText = MutableStateFlow("")
    val searchText = _searchText

    private val _isSearching = MutableStateFlow(false)
    val isSearching = _isSearching

    private val _musics = MutableStateFlow<List<Music>>(emptyList())
    val movies = _searchText.combine(_musics) { text, movies ->
        if (text.isBlank()) {
            movies
        } else {
            movies.filter { it.doesMatchSearchQuery(text) }
        }
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        emptyList()
    )

    init {
        loadMovies()
    }

    fun onSearchTextChange(text: String) {
        _searchText.value = text
    }

    private fun loadMovies() {
        viewModelScope.launch {
            try {
                val musicList = getMusicsFromDB()
                _musics.value = musicList
            } catch (e: Exception) {
                Log.e("SearchViewModel", "Error", e)
            }
        }
    }

    private suspend fun getMusicsFromDB(): List<Music> {
        val snapshot = FirebaseDatabase.getInstance().getReference("musics").get().await()
        val listMusic = mutableListOf<Music>()
        snapshot.children.forEach { dataSnap ->
            val music = Music().apply {
                id = dataSnap.key!!
                title = dataSnap.child("title").value.toString()
                image = dataSnap.child("image").value.toString()
                author = dataSnap.child("author").value.toString()
            }
            listMusic.add(music)
        }
        return listMusic
    }
}