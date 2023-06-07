package com.example.iitrace.view.authentication

import com.example.iitrace.network.data.responses.AlertsResponse

class AlertsState (
    var isLoading:Boolean = false,
    var data: ArrayList<AlertsResponse>? = null,
    var error: String = ""
)