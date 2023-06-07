package com.example.iitrace.domain.repository

import com.example.iitrace.network.data.IITraceAPI
import com.example.iitrace.network.data.requests.CreateRepRequest
import com.example.iitrace.network.data.responses.HistoryResponse
import com.example.iitrace.network.data.requests.LoginRequest
import com.example.iitrace.network.data.requests.ScanQRERequest
import com.example.iitrace.network.data.requests.ScanQRRequest
import com.example.iitrace.network.data.responses.AlertsResponse
import com.example.iitrace.network.data.responses.CreateRepResponse
import com.example.iitrace.network.data.responses.DiseasesResponse
import com.example.iitrace.network.data.responses.LoginResponse
import com.example.iitrace.network.data.responses.ReportsResponse
import com.example.iitrace.network.data.responses.ScanQREResponse
import com.example.iitrace.network.data.responses.ScanQRResponse
import javax.inject.Inject

class IITraceRepository @Inject constructor(
    private val api: IITraceAPI
) {
    suspend fun scans(token: Map<String, String>, request: ScanQRRequest): ScanQRResponse = api.scans(token, request)
    suspend fun exitscans(token: Map<String, String>, request: ScanQRERequest): ScanQREResponse = api.exitscans(token, request)
    suspend fun login(request: LoginRequest): LoginResponse = api.login(request)
    suspend fun history(token: Map<String, String>): ArrayList<HistoryResponse> = api.history(token)
    suspend fun reports(token: Map<String, String>): ArrayList<ReportsResponse> = api.reports(token)
    suspend fun diseases(token: Map<String, String>): ArrayList<DiseasesResponse> = api.diseases(token)
    suspend fun createrep(token: Map<String, String>, request: CreateRepRequest): CreateRepResponse = api.createrep(token, request)
    suspend fun alerts(token: Map<String, String>): ArrayList<AlertsResponse> = api.alerts(token)
}