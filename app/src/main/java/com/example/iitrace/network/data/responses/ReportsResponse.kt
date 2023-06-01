package com.example.iitrace.network.data.responses

import com.google.gson.annotations.SerializedName
import java.util.*

data class ReportsResponse (
    @SerializedName("id")
    var id: Int,
    @SerializedName("entry")
    var entry: Int,
    @SerializedName("disease")
    var disease: Int,
    @SerializedName("disease_name")
    var disease_name: String,
    @SerializedName("date_created")
    var date_created: Date,
)