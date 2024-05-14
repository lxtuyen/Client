package com.example.heartsteel.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Albums(
    var id: String? = null,
    var title: String? = "Empty title",
    var subtitle: String? = null,
    var author: String? = null,
    var imageRes: String? = null,
    var data: List<Music> = emptyList(),
) : Parcelable {
}
