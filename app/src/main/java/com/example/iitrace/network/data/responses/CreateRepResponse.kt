package com.example.iitrace.network.data.responses

import com.google.gson.annotations.SerializedName
import java.util.Date

data class CreateRepResponse(
    @SerializedName("message")
    val message: String,
    @SerializedName("details")
    var details: Data
) {
    data class Data (
        @SerializedName("id")
        val id: Int,
        @SerializedName("entry")
        val entry: Int,
        @SerializedName("disease")
        val disease: Int,
        @SerializedName("disease_name")
        val disease_name: String,
        @SerializedName("date_created")
        val date_created: Date
    )
}