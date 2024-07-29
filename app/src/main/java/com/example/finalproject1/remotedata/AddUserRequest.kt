package com.example.finalproject1.remotedata

data class AddUserRequest(
    val email_id: String,
    val full_name: String,
    val mobile_no: String,
    val password: String
)