package com.nikhilchauhan.housefacility.di.module

import com.nikhilchauhan.housefacility.constants.Constants
import com.nikhilchauhan.housefacility.data.remote.api.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

  @Singleton
  @Provides
  fun provideOkHttpClient(): OkHttpClient =
    OkHttpClient.Builder().readTimeout(15, TimeUnit.SECONDS)
      .connectTimeout(15, TimeUnit.SECONDS)
      .build()

  @Singleton
  @Provides
  fun providerGsonConvertorFactory(): GsonConverterFactory = GsonConverterFactory.create()

  @Singleton
  @Provides
  fun provideRetrofit(
    okHttpClient: OkHttpClient
  ): Retrofit = Retrofit.Builder().baseUrl(Constants.BASE_URL)
    .client(okHttpClient).build()

  @Singleton
  @Provides
  fun provideApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)
}