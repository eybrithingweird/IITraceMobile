package com.example.iitrace.network.data

import com.example.iitrace.network.data.requests.HistoryRequest
import com.example.iitrace.network.data.responses.HistoryResponse
import com.example.iitrace.network.data.requests.LoginRequest
import com.example.iitrace.network.data.requests.ScanQRRequest
import com.example.iitrace.network.data.responses.LoginResponse
import com.example.iitrace.network.data.responses.ScanQRResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface IITraceAPI {
    companion object{
        const val LOGIN = "api/login/"
        const val SCANS = "api/entries/create/user/"
        const val HISTORY = "api/entries/list/user/"
    }

    @POST(LOGIN)
    suspend fun login(@Body loginRequest: LoginRequest): LoginResponse

    @POST(SCANS)
    suspend fun scans(@Body scanRequest: ScanQRRequest): ScanQRResponse

    @POST(HISTORY)
    suspend fun history(@Body historyRequest: HistoryRequest): HistoryResponse

}