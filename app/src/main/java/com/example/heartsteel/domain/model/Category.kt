package com.example.heartsteel.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Category(
    val id: Int = 0,
    var title: String? = "Empty title",
    var subtitle: String? = null,
    var author: Music? = null,
    var imageRes: Int? = null,
    var data: List<Music> = emptyList(),
) : Parcelable {
    companion object {
        val Default = Category(0, subtitle = "empty subtitle")
    }

    fun authors(): List<String> = data.map { it.title ?: "" }
}
