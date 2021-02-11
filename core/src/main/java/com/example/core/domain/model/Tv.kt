package com.example.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Tv(
    val id: Int,
    val name: String,
    val overview: String,
    val posterPath: String,
    val firstAirDate: String,
    val voteAverage: Double,
    var favorite: Boolean = false
) : Parcelable