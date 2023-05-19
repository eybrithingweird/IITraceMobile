package com.example.iitrace.network.data.requests

import com.google.gson.annotations.SerializedName

data class ScanQRRequest (
    @SerializedName("room")
    val room: String,
    @SerializedName("building")
    val building: String,
    @SerializedName("datetime")
    val datetime: String
)