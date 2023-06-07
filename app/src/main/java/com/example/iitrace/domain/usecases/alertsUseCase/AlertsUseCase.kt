package com.example.iitrace.domain.usecases.alertsUseCase

import com.example.iitrace.domain.repository.IITraceRepository
import com.example.iitrace.network.data.responses.AlertsResponse
import com.example.iitrace.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class AlertsUseCase @Inject constructor(private val repository: IITraceRepository){
    operator fun invoke(token: Map<String, String>): Flow<Resource<ArrayList<AlertsResponse>>> = flow {
        try {
            emit(Resource.Loading())
            val response = repository.alerts(token)
            emit(Resource.Success(response))
        } catch (e: HttpException) {
            emit(Resource.Error("An error occurred"))
        } catch (e: IOException) {
            emit(Resource.Error("No internet connection"))
        }
    }
}