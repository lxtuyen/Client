package com.example.heartsteel.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Category(
    var id: String? = null,
    var title: String? = "Empty title",
    var data: List<Music> = emptyList(),
) : Parcelable {
}
