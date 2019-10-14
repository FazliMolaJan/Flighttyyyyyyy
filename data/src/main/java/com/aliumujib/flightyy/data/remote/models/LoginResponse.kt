package com.aliumujib.flightyy.data.remote.models

data class LoginResponse(
    val access_token: String,
    val expires_in: Int,
    val token_type: String
)