package com.example.iitrace.network.data.requests

import com.google.gson.annotations.SerializedName

data class CreateRepRequest(
//    @SerializedName("entry")
//    val entry: Int,
    @SerializedName("disease")
    val disease: Int
)