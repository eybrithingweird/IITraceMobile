package com.example.iitrace.view.authentication

import com.example.iitrace.network.data.responses.CreateRepResponse

class CreateRepState (
    var isLoading:Boolean = false,
    var data: CreateRepResponse? = null,
    var error: String = ""
)