package com.example.finalproject1.remotedata

import com.example.finalproject1.data.Subcategory

data class SubCategoryResponse(
    val message: String,
    val status: Int,
    val subcategories: List<Subcategory>
)