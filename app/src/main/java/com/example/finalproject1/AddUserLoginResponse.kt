package com.example.finalproject1

data class AddUserLoginResponse(
    val message: String,
    val status: Int,
    val user: User?
)