package com.example.finalproject1.data

import android.os.Parcelable
import androidx.annotation.DrawableRes
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ProductData( val id: Long,
                        val name: String,
                        val description: String,
                        val price: Double,
                        @DrawableRes val image: Int):Parcelable
