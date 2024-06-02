package com.example.heartsteel.domain.model

import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class Tabs (
    val id: Int = 0,
    val title: String? = null,
) : Parcelable