package com.example.finalproject1.remotedata

import com.example.finalproject1.data.CategoryData

data class GetCategoryResponse(
    val status:Int,
    val message:String,
    val categories:List<CategoryData>
)
