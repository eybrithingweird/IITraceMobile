package com.example.iitrace.network.data

import com.example.iitrace.network.data.requests.LoginRequest
import com.example.iitrace.network.data.requests.ScanQRRequest
import com.example.iitrace.network.data.responses.HistoryResponse
import com.example.iitrace.network.data.responses.LoginResponse
import com.example.iitrace.network.data.responses.ScanQRResponse
import com.google.gson.Gson
import retrofit2.Callback
import retrofit2.http.*


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

    @GET(HISTORY)
    suspend fun history(@HeaderMap header: Map<String, String>): ArrayList<HistoryResponse>
//    suspend fun history(
//        @Header("Authentication") token: String?,
//    ): Call<HistoryInfo?>?


//    fun getHistoryEntries(token: String?, callback: Callback<HistoryInfo>?) {
//        val historyEntries: Call<HistoryInfo> =
//            service.getHistoryEntries(token)
//        historyEntries.enqueue(callback)
//    }

}