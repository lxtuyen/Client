package com.example.heartsteel.model

import android.os.Parcelable
import com.example.heartsteel.R
import kotlinx.parcelize.Parcelize

@Parcelize
data class Music(
    val id: Int = 0,
    val categoryId: Int = 0,
    var genreId:Int = 0,
    var audio: String? = null,
    var image: Int? = null,
    var images: List<Int>? = null,
    var title: String? = null,
    var subtitle: String? = null,
    var description: String? = null,
    var tag: String? = null,
    var likes: String? = null,
    var duration: String? = null,
) : Parcelable {
    companion object {
        val Default = Music(title = "title", subtitle = "subtitle", image = R.drawable.album)
    }
}
