package com.example.heartsteel.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class Genre(
    val id: Int = 0,
    val title: String? = null,
    val image: Int? = null,
) : Parcelable
