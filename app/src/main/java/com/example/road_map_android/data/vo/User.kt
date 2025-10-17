package com.example.road_map_android.data.vo

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val name: String,
    val age: Int
) : Parcelable