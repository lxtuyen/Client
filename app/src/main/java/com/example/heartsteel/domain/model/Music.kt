package com.example.heartsteel.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Music(
    var id: String? = null,
    var genre:String? = null,
    var audio: String? = null,
    var image: String? = null,
    var title: String? = null,
    var lyrics: String? = null,
    var tag: String? = null,
    var likes: String? = null,
    var author: String? = null,
) : Parcelable {
}
