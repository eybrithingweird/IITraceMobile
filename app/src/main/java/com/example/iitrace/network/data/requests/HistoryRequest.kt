package com.example.iitrace.network.data.requests

import com.google.gson.annotations.SerializedName

data class HistoryRequest (
    @SerializedName("authorization")
    val authorization: String,
)