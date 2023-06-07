package com.example.iitrace.network.data.responses

import com.google.gson.annotations.SerializedName
import java.util.Date

data class DiseasesResponse (
    @SerializedName("id")
    var id: Int,
    @SerializedName("name")
    var name: String,
    @SerializedName("incubation_duration")
    var incubation_duration: String,
    @SerializedName("infection_duration")
    var infection_duration: String,
)