package com.example.finalproject1

import com.google.gson.annotations.SerializedName

data class UserRegisterInfo(
    @SerializedName("email_id")
    val email_id: String,
    @SerializedName("full_name")
    val full_name: String,
    @SerializedName("mobile_no")
    val mobile_no: String,
    @SerializedName("password")
    val password: String)
