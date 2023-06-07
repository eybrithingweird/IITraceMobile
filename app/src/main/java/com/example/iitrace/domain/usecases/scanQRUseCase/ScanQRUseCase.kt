package com.example.iitrace.domain.usecases.scanQRUseCase

import android.util.Log
import com.example.iitrace.domain.repository.IITraceRepository
import com.example.iitrace.network.data.requests.ScanQRRequest
import com.example.iitrace.network.data.responses.ScanQRResponse
import com.example.iitrace.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class ScanQRUseCase @Inject constructor(private val repository: IITraceRepository){
    operator fun invoke(token: Map<String, String>, request: ScanQRRequest): Flow<Resource<ScanQRResponse>> = flow {
        try {
            emit(Resource.Loading())
            var response = repository.scans(token, request)
            emit(Resource.Success(response))
        } catch (e: HttpException) {
            emit(Resource.Error("An error occurred"))
        } catch (e: IOException) {
            emit(Resource.Error("No internet connection"))
        }
    }
}