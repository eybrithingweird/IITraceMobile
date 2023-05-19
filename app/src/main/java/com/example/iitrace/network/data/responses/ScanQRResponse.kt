package com.example.iitrace.network.data.responses

import com.google.gson.annotations.SerializedName

data class ScanQRResponse (
    @SerializedName("success")
    val success: String,
    @SerializedName("refresh")
    val refresh: String
)