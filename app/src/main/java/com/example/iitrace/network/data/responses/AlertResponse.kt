package com.example.iitrace.network.data.responses

import com.google.gson.annotations.SerializedName

data class AlertResponse (
    @SerializedName("access")
    val access: String,
    @SerializedName("datetime")
    val datetime: String,
    @SerializedName("message")
    val message: String
)