package com.example.iitrace.network.data

import com.example.iitrace.network.data.requests.LoginRequest
import com.example.iitrace.network.data.requests.ScanQRRequest
import com.example.iitrace.network.data.responses.HistoryResponse
import com.example.iitrace.network.data.responses.LoginResponse
import com.example.iitrace.network.data.responses.ReportsResponse
import com.example.iitrace.network.data.responses.ScanQRResponse
import retrofit2.http.*


interface IITraceAPI {
    companion object{
        const val LOGIN = "api/login/"
        const val SCANS = "api/entries/create/user/"
        const val HISTORY = "api/entries/list/user/"
        const val REPORTS = "api/reports/list/user/"
    }

    @POST(LOGIN)
    suspend fun login(@Body loginRequest: LoginRequest): LoginResponse

    @POST(SCANS)
    suspend fun scans(@HeaderMap header: Map<String, String>, @Body scanRequest: ScanQRRequest): ScanQRResponse

    @GET(HISTORY)
    suspend fun history(@HeaderMap header: Map<String, String>): ArrayList<HistoryResponse>

    @GET(REPORTS)
    suspend fun reports(@HeaderMap header: Map<String, String>): ArrayList<ReportsResponse>

}