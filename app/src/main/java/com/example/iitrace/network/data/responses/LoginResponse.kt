package com.example.iitrace.network.data.responses

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("expiry")
    val access: String,
    @SerializedName("token")
    var token: String,
    @SerializedName("user_data")
    var `user_data`: Data

) {
    data class Data(
        @SerializedName("email")
        var email: String,
        @SerializedName("id")
        var id: String,
        @SerializedName("username")
        var username: String
    )
}