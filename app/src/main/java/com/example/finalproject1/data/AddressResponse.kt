package com.example.finalproject1.data

data class AddressResponse(
    val addresses: List<Address>,
    val message: String,
    val status: Int
)