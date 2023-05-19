package com.example.iitrace.network.data.requests

import com.google.gson.annotations.SerializedName

data class LoginRequest(
    @SerializedName("username")
    val email: String,
    @SerializedName("password")
    val password: String
)