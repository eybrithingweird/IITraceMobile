package com.example.iitrace.view.authentication

import com.example.iitrace.network.data.responses.HistoryResponse

class HistoryState (
    var isLoading:Boolean = false,
    var data: HistoryResponse? = null,
    var error: String = ""
)