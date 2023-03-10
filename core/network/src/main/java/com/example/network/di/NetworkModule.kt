package com.example.network.di

import com.example.network.BuildConfig
import com.example.network.datasource.RemoteProjectDataSource
import com.example.network.datasource.RemoteProjectDataSourceImpl
import com.example.network.datasource.RemoteUserDataSource
import com.example.network.datasource.RemoteUserDataSourceImpl
import com.example.network.remote.ApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
interface NetworkModule {
    @Binds
    fun bindRemoteUserDataSource(dataSourceImpl: RemoteUserDataSourceImpl): RemoteUserDataSource

    @Binds
    fun bindRemoteProjectDataSource(dataSourceImpl: RemoteProjectDataSourceImpl): RemoteProjectDataSource

    companion object {
        @Provides
        fun provideAuthService(): ApiService = createRetrofit().create(ApiService::class.java)

        @OptIn(ExperimentalSerializationApi::class)
        private fun createRetrofit() = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
            .client(okHttp())
            .build()

        private fun okHttp(): OkHttpClient {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BASIC
            return OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .addInterceptor { chain ->
                    val request =
                        chain.request().newBuilder().addHeader("Accept", "application/json").build()
                    chain.proceed(request)
                }
                .build()
        }


    }
}