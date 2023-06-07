package com.example.iitrace.domain.usecases.createrepUseCase

import com.example.iitrace.domain.repository.IITraceRepository
import com.example.iitrace.network.data.requests.CreateRepRequest
import com.example.iitrace.network.data.responses.CreateRepResponse
import com.example.iitrace.network.data.responses.DiseasesResponse
import com.example.iitrace.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class CreateRepUseCase @Inject constructor(private val repository: IITraceRepository){
    operator fun invoke(token: Map<String, String>, request: CreateRepRequest): Flow<Resource<CreateRepResponse>> = flow {
        try {
            emit(Resource.Loading())
            val response = repository.createrep(token, request)
            emit(Resource.Success(response))
        } catch (e: HttpException) {
            emit(Resource.Error("An error occurred"))
        } catch (e: IOException) {
            emit(Resource.Error("No internet connection"))
        }
    }
}