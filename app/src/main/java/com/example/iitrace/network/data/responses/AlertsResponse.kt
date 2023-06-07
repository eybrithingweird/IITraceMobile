package com.example.iitrace.network.data.responses

import com.google.gson.annotations.SerializedName
import java.util.Date

data class AlertsResponse(
    @SerializedName("id")
    val id: Int,
    @SerializedName("alert_message")
    var alert_message: String,
    @SerializedName("date_created")
    var date_created: String,
    @SerializedName("alert")
    var alert: Int,
)