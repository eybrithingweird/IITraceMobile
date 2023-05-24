package com.example.iitrace.view.authentication

import com.example.iitrace.network.data.responses.HistoryResponse
import retrofit2.Callback

class HistoryState(
    var isLoading:Boolean = false,
    var data: ArrayList<HistoryResponse>? = null,
    var error: String = ""
)