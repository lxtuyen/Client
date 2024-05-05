package com.example.heartsteel.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    var id: String? = null,
    var email: String? = null,
    var password: String? = null,
    var username: String? = null,
    val profilePictureUrl: String?= null,
    var albums: List<Music> = emptyList(),
    var history: List<Music> = emptyList(),
) : Parcelable {
    companion object {
        val Default = User(email = "", password = "")
    }
}
