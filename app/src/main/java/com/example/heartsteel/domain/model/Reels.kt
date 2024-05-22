package com.example.heartsteel.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Reels (
    var id:String? = null,
    var url:String? = null,
    var author: String? = null,
    var caption:String? = null,
    ) : Parcelable {
    companion object {
        val Default = Reels(caption = "empty caption", author = "author")
    }
}