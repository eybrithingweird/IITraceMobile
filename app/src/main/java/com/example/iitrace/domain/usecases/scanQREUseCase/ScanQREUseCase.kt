package com.example.iitrace.domain.usecases.scanQREUseCase

import com.example.iitrace.domain.repository.IITraceRepository
import com.example.iitrace.network.data.requests.ScanQRERequest
import com.example.iitrace.network.data.requests.ScanQRRequest
import com.example.iitrace.network.data.responses.ScanQREResponse
import com.example.iitrace.network.data.responses.ScanQRResponse
import com.example.iitrace.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class ScanQREUseCase @Inject constructor(private val repository: IITraceRepository){
    operator fun invoke(token: Map<String, String>, request: ScanQRERequest): Flow<Resource<ScanQREResponse>> = flow {
        try {
            emit(Resource.Loading())
            var response = repository.exitscans(token, request)
            emit(Resource.Success(response))
        } catch (e: HttpException) {
            emit(Resource.Error("An error occurred"))
        } catch (e: IOException) {
            emit(Resource.Error("No internet connection"))
        }
    }
}