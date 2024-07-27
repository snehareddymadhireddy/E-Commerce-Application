package com.example.finalproject1.data

import android.os.Parcelable
import androidx.annotation.DrawableRes
import kotlinx.parcelize.Parcelize

@Parcelize
data class Item(
    val name: String,
    val description: String,
    val price: Double,
    @DrawableRes val image: Int
): Parcelable
