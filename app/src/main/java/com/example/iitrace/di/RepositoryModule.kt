package com.example.iitrace.di

import com.example.iitrace.domain.repository.IITraceRepository
import com.example.iitrace.network.data.IITraceAPI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {
    @Singleton
    @Provides
    fun provideIItraceRepository(api: IITraceAPI): IITraceRepository = IITraceRepository(api)
}