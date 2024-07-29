package com.example.finalproject1.data

data class ProductResponse(
    val message: String,
    val products: List<Product>,
    val status: Int
)