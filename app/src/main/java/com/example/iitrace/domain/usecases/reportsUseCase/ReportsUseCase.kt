package com.example.iitrace.domain.usecases.reportsUseCase

import com.example.iitrace.domain.repository.IITraceRepository
import com.example.iitrace.network.data.responses.HistoryResponse
import com.example.iitrace.network.data.responses.ReportsResponse
import com.example.iitrace.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class ReportsUseCase @Inject constructor(private val repository: IITraceRepository){
    operator fun invoke(request: Map<String, String>): Flow<Resource<ArrayList<ReportsResponse>>> = flow {
        try {
            emit(Resource.Loading())
            val response = repository.reports(request)
            emit(Resource.Success(response))
        } catch (e: HttpException) {
            emit(Resource.Error("An error occurred"))
        } catch (e: IOException) {
            emit(Resource.Error("No internet connection"))
        }
    }
}