package com.example.iitrace.view.authentication

import com.example.iitrace.network.data.responses.ScanQREResponse

class ScanQREState (
    var isLoading:Boolean = false,
    var data: ScanQREResponse? = null,
    var error: String = ""
)