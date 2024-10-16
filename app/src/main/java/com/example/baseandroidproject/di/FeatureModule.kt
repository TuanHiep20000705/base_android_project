package com.example.baseandroidproject.di

import com.example.baseandroidproject.network.ServiceApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FeatureModule {
    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): ServiceApi = retrofit.create(ServiceApi::class.java)
}