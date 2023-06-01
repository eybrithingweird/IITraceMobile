package com.example.iitrace.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.iitrace.domain.usecases.loginUseCase.LoginUseCase
import com.example.iitrace.domain.usecases.historyUseCase.HistoryUseCase
import com.example.iitrace.domain.usecases.reportsUseCase.ReportsUseCase
import com.example.iitrace.domain.usecases.scanQRUseCase.ScanQRUseCase
import com.example.iitrace.network.data.requests.LoginRequest
import com.example.iitrace.network.data.requests.ScanQRRequest
import com.example.iitrace.util.Resource
import com.example.iitrace.view.authentication.HistoryState
import com.example.iitrace.view.authentication.LoginState
import com.example.iitrace.view.authentication.ReportsState
import com.example.iitrace.view.authentication.ScanQRState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class IITraceViewModel @Inject constructor(
    private val scanQRUseCase: ScanQRUseCase,
    private val loginUseCase: LoginUseCase,
    private val historyUseCase: HistoryUseCase,
    private val reportsUseCase: ReportsUseCase,
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
}