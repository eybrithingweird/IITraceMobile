package com.example.iitrace.network.data.requests

import com.google.gson.annotations.SerializedName

data class ScanQRRequest (
    @SerializedName("person")
    val person: Int,
    @SerializedName("room")
    val room: String,
)