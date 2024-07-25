package com.example.finalproject1

data class GetCategoryResponse(
    val status:Int,
    val message:String,
    val categories:List<CategoryData>
)
