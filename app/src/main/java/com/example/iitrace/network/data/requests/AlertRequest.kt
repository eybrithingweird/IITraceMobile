package com.example.iitrace.network.data.requests

import com.google.gson.annotations.SerializedName

data class AlertRequest (
    @SerializedName("id")
    val id: Int,
    @SerializedName("authorization")
    val authorization: String,
)