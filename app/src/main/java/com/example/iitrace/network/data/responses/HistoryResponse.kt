package com.example.iitrace.network.data.responses

import com.google.gson.annotations.SerializedName
import java.util.*

data class HistoryResponse (
    @SerializedName("success")
    val success: String,
    @SerializedName("refresh")
    val refresh: String,
    @SerializedName("user_data")
    var `user_data`: Data
) {
    data class Data(
        @SerializedName("id")
        var id: Int,
        @SerializedName("date_entered")
        var date_entered: Date,
        @SerializedName("date_exited")
        var date_exited: Date,
        @SerializedName("date_created")
        var date_created: Date,
        @SerializedName("date_updated")
        var date_updated: Date,
        @SerializedName("person")
        var person: String,
        @SerializedName("room")
        var room: Int,
        @SerializedName("room_name")
        var room_name: String,
        @SerializedName("building_name")
        var building_name: Int,
    )
}