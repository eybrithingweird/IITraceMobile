package com.example.iitrace.view.authentication

import com.example.iitrace.network.data.responses.ScanQRResponse

class ScanQRState (
    var isLoading:Boolean = false,
    var data: ScanQRResponse? = null,
    var error: String = ""
)