package com.example.finalproject1

import com.example.finalproject1.data.User

data class AddUserLoginResponse(
    val message: String,
    val status: Int,
    val user: User?
)