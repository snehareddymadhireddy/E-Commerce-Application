package com.example.finalproject1

import androidx.annotation.DrawableRes

data class ProductData( val id: Long,
                        val name: String,
                        val description: String,
                        val price: Double,
                        @DrawableRes val image: Int)
