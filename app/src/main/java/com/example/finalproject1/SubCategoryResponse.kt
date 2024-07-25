package com.example.finalproject1

data class SubCategoryResponse(
    val message: String,
    val status: Int,
    val subcategories: List<Subcategory>
)