package com.example.iitrace.domain.usecases.historyUseCase

import com.example.iitrace.domain.repository.IITraceRepository
import com.example.iitrace.network.data.requests.HistoryRequest
import com.example.iitrace.network.data.requests.LoginRequest
import com.example.iitrace.network.data.responses.HistoryResponse
import com.example.iitrace.network.data.responses.LoginResponse
import com.example.iitrace.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class HistoryUseCase @Inject constructor(private val repository: IITraceRepository){
    operator fun invoke(request: HistoryRequest): Flow<Resource<HistoryResponse>> = flow {
        try {
            emit(Resource.Loading())
            val response = repository.history(request)
            emit(Resource.Success(response))
        } catch (e: HttpException) {
            emit(Resource.Error("An error occurred"))
        } catch (e: IOException) {
            emit(Resource.Error("No internet connection"))
        }
    }
}