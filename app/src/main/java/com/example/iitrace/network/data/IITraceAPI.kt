package com.example.iitrace.network.data

import com.example.iitrace.network.data.requests.CreateRepRequest
import com.example.iitrace.network.data.requests.LoginRequest
import com.example.iitrace.network.data.requests.ScanQRERequest
import com.example.iitrace.network.data.requests.ScanQRRequest
import com.example.iitrace.network.data.responses.AlertsResponse
import com.example.iitrace.network.data.responses.CreateRepResponse
import com.example.iitrace.network.data.responses.DiseasesResponse
import com.example.iitrace.network.data.responses.HistoryResponse
import com.example.iitrace.network.data.responses.LoginResponse
import com.example.iitrace.network.data.responses.ReportsResponse
import com.example.iitrace.network.data.responses.ScanQREResponse
import com.example.iitrace.network.data.responses.ScanQRResponse
import retrofit2.http.*


interface IITraceAPI {
    companion object{
        const val LOGIN = "api/login/"
        const val SCANS = "api/entries/create/user/"
        const val EXITSCANS = "api/entries/update_date_exited/"
        const val HISTORY = "api/entries/list/user/"
        const val REPORTS = "api/reports/list/user/"
        const val DISEASES = "api/diseases/all/"
        const val CREATEREP = "api/reports/create/user/"
        const val ALERTS = "api/alert-recipients/user/"
    }

    @POST(LOGIN)
    suspend fun login(@Body loginRequest: LoginRequest): LoginResponse

    @POST(SCANS)
    suspend fun scans(@HeaderMap header: Map<String, String>, @Body scanRequest: ScanQRRequest): ScanQRResponse

    @PUT(EXITSCANS)
    suspend fun exitscans(@HeaderMap header: Map<String, String>, @Body scanRequest: ScanQRERequest): ScanQREResponse

    @GET(HISTORY)
    suspend fun history(@HeaderMap header: Map<String, String>): ArrayList<HistoryResponse>

    @GET(REPORTS)
    suspend fun reports(@HeaderMap header: Map<String, String>): ArrayList<ReportsResponse>

    @GET(DISEASES)
    suspend fun diseases(@HeaderMap header: Map<String, String>): ArrayList<DiseasesResponse>

    @POST(CREATEREP)
    suspend fun createrep(@HeaderMap header: Map<String, String>, @Body createRepRequest: CreateRepRequest): CreateRepResponse

    @GET(ALERTS)
    suspend fun alerts(@HeaderMap header: Map<String, String>): ArrayList<AlertsResponse>

}