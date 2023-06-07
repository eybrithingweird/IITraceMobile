package com.example.iitrace.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.iitrace.domain.usecases.alertsUseCase.AlertsUseCase
import com.example.iitrace.domain.usecases.createrepUseCase.CreateRepUseCase
import com.example.iitrace.domain.usecases.diseasesUseCase.DiseasesUseCase
import com.example.iitrace.domain.usecases.loginUseCase.LoginUseCase
import com.example.iitrace.domain.usecases.historyUseCase.HistoryUseCase
import com.example.iitrace.domain.usecases.reportsUseCase.ReportsUseCase
import com.example.iitrace.domain.usecases.scanQREUseCase.ScanQREUseCase
import com.example.iitrace.domain.usecases.scanQRUseCase.ScanQRUseCase
import com.example.iitrace.network.data.requests.CreateRepRequest
import com.example.iitrace.network.data.requests.LoginRequest
import com.example.iitrace.network.data.requests.ScanQRERequest
import com.example.iitrace.network.data.requests.ScanQRRequest
import com.example.iitrace.util.Resource
import com.example.iitrace.view.authentication.AlertsState
import com.example.iitrace.view.authentication.CreateRepState
import com.example.iitrace.view.authentication.DiseasesState
import com.example.iitrace.view.authentication.HistoryState
import com.example.iitrace.view.authentication.LoginState
import com.example.iitrace.view.authentication.ReportsState
import com.example.iitrace.view.authentication.ScanQREState
import com.example.iitrace.view.authentication.ScanQRState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class IITraceViewModel @Inject constructor(
    private val scanQRUseCase: ScanQRUseCase,
    private val scanQREUseCase: ScanQREUseCase,
    private val loginUseCase: LoginUseCase,
    private val historyUseCase: HistoryUseCase,
    private val reportsUseCase: ReportsUseCase,
    private val diseasesUseCase: DiseasesUseCase,
    private val createrepUseCase: CreateRepUseCase,
    private val alertsUseCase: AlertsUseCase
) : ViewModel() {
    private val scanQRState: MutableLiveData<ScanQRState> = MutableLiveData()
    val _scanQRState: LiveData<ScanQRState>
        get() = scanQRState

    fun scans(tokenString: Map<String, String>, scanqrRequest: ScanQRRequest){
        scanQRUseCase(tokenString, scanqrRequest).onEach { result ->
            when(result){
                is Resource.Success<*> ->{
                    scanQRState.value = ScanQRState(data = result.data)
                }

                is Resource.Loading<*> -> {
                    scanQRState.value = ScanQRState(isLoading = true)
                }

                is Resource.Error<*> -> {
                    scanQRState.value = result.message?.let { ScanQRState(error = it) }
                }
            }
        }.launchIn(viewModelScope)
    }

    private val scanQREState: MutableLiveData<ScanQREState> = MutableLiveData()
    val _scanQREState: LiveData<ScanQREState>
        get() = scanQREState

    fun exitscans(tokenString: Map<String, String>, scanqreRequest: ScanQRERequest){
        scanQREUseCase(tokenString, scanqreRequest).onEach { result ->
            when(result){
                is Resource.Success<*> ->{
                    scanQREState.value = ScanQREState(data = result.data)
                }

                is Resource.Loading<*> -> {
                    scanQREState.value = ScanQREState(isLoading = true)
                }

                is Resource.Error<*> -> {
                    scanQREState.value = result.message?.let { ScanQREState(error = it) }
                }
            }
        }.launchIn(viewModelScope)
    }

    private val loginState: MutableLiveData<LoginState> = MutableLiveData()
    val _loginState: LiveData<LoginState>
        get() = loginState

    fun login(loginRequest: LoginRequest){
        loginUseCase(loginRequest).onEach { result ->
            when(result){
                is Resource.Success<*> ->{
                    loginState.value = LoginState(data = result.data)
                }

                is Resource.Loading<*> -> {
                    loginState.value = LoginState(isLoading = true)
                }

                is Resource.Error<*> -> {
                    loginState.value = result.message?.let { LoginState(error = it) }
                }
            }
        }.launchIn(viewModelScope)
    }

    private val historyState: MutableLiveData<HistoryState> = MutableLiveData()
    val _historyState: MutableLiveData<HistoryState>
        get() = historyState

    fun history(tokenString: Map<String, String>){
        historyUseCase(tokenString).onEach { result ->
            when(result){
                is Resource.Success<*> ->{
                    historyState.value = HistoryState(data = result.data)
                }

                is Resource.Loading<*> -> {
                    historyState.value = HistoryState(isLoading = true)
                }

                is Resource.Error<*> -> {
                    historyState.value = result.message?.let { HistoryState(error = it) }
                }
            }
        }.launchIn(viewModelScope)
    }

    private val reportsState: MutableLiveData<ReportsState> = MutableLiveData()
    val _reportsState: MutableLiveData<ReportsState>
        get() = reportsState

    fun reports(tokenString: Map<String, String>){
        reportsUseCase(tokenString).onEach { result ->
            when(result){
                is Resource.Success<*> ->{
                    reportsState.value = ReportsState(data = result.data)
                }

                is Resource.Loading<*> -> {
                    reportsState.value = ReportsState(isLoading = true)
                }

                is Resource.Error<*> -> {
                    reportsState.value = result.message?.let { ReportsState(error = it) }
                }
            }
        }.launchIn(viewModelScope)
    }

    private val diseasesState: MutableLiveData<DiseasesState> = MutableLiveData()
    val _diseasesState: MutableLiveData<DiseasesState>
        get() = diseasesState

    fun diseases(tokenString: Map<String, String>){
        diseasesUseCase(tokenString).onEach { result ->
            when(result){
                is Resource.Success<*> ->{
                    diseasesState.value = DiseasesState(data = result.data)
                }

                is Resource.Loading<*> -> {
                    diseasesState.value = DiseasesState(isLoading = true)
                }

                is Resource.Error<*> -> {
                    diseasesState.value = result.message?.let { DiseasesState(error = it) }
                }
            }
        }.launchIn(viewModelScope)
    }

    private val createrepState: MutableLiveData<CreateRepState> = MutableLiveData()
    val _createrepState: MutableLiveData<CreateRepState>
        get() = createrepState

    fun createrep(tokenString: Map<String, String>, createrepRequest: CreateRepRequest){
        createrepUseCase(tokenString, createrepRequest).onEach { result ->
            when(result){
                is Resource.Success<*> ->{
                    createrepState.value = CreateRepState(data = result.data)
                }

                is Resource.Loading<*> -> {
                    createrepState.value = CreateRepState(isLoading = true)
                }

                is Resource.Error<*> -> {
                    createrepState.value = result.message?.let { CreateRepState(error = it) }
                }
            }
        }.launchIn(viewModelScope)
    }

    private val alertsState: MutableLiveData<AlertsState> = MutableLiveData()
    val _alertsState: MutableLiveData<AlertsState>
        get() = alertsState

    fun alerts(tokenString: Map<String, String>){
        alertsUseCase(tokenString).onEach { result ->
            when(result){
                is Resource.Success<*> ->{
                    alertsState.value = AlertsState(data = result.data)
                }

                is Resource.Loading<*> -> {
                    alertsState.value = AlertsState(isLoading = true)
                }

                is Resource.Error<*> -> {
                    alertsState.value = result.message?.let { AlertsState(error = it) }
                }
            }
        }.launchIn(viewModelScope)
    }
}