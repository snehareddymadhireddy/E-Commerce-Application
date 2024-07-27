package com.example.finalproject1.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Address(val name: String, val address: String) : Parcelable

