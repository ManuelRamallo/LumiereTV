package com.mramallo.lumieretv.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class BelongsToCollection(
    val backdrop_path: String = "",
    val id: Int = 0,
    val name: String = "",
    val poster_path: String = ""
) : Parcelable