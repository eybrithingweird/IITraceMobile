package com.example.iitrace.view.authentication

import com.example.iitrace.network.data.responses.HistoryResponse
import com.example.iitrace.network.data.responses.ReportsResponse
import retrofit2.Callback

class ReportsState(
    var isLoading:Boolean = false,
    var data: ArrayList<ReportsResponse>? = null,
    var error: String = ""
)