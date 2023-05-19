package com.example.iitrace.domain.repository

import com.example.iitrace.network.data.IITraceAPI
import com.example.iitrace.network.data.requests.HistoryRequest
import com.example.iitrace.network.data.responses.HistoryResponse
import com.example.iitrace.network.data.requests.LoginRequest
import com.example.iitrace.network.data.requests.ScanQRRequest
import com.example.iitrace.network.data.responses.LoginResponse
import com.example.iitrace.network.data.responses.ScanQRResponse
import javax.inject.Inject

class IITraceRepository @Inject constructor(
    private val api: IITraceAPI
) {
    suspend fun scans(request: ScanQRRequest): ScanQRResponse = api.scans(request)
    suspend fun login(request: LoginRequest): LoginResponse = api.login(request)
    suspend fun history(request: HistoryRequest) : HistoryResponse = api.history(request)
}