package com.example.finalproject1.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cartItems")
data class Product(
    val average_rating: String,
    val category_id: String,
    val category_name: String,
    val description: String,
    val price: String,
    @PrimaryKey val product_id: String,
    val product_image_url: String,
    val product_name: String,
    val sub_category_id: String,
    val subcategory_name: String,
    var quantity:Int= 0
)