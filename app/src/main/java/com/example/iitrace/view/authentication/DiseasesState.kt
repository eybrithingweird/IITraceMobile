package com.example.iitrace.view.authentication

import com.example.iitrace.network.data.responses.DiseasesResponse

class DiseasesState (
    var isLoading:Boolean = false,
    var data: ArrayList<DiseasesResponse>? = null,
    var error: String = ""
)