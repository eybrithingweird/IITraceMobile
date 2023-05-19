package com.example.iitrace.view.authentication

import com.example.iitrace.network.data.responses.LoginResponse

class LoginState (
    var isLoading:Boolean = false,
    var data: LoginResponse? = null,
    var error: String = ""
)