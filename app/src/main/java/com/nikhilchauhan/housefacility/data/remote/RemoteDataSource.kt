package com.nikhilchauhan.housefacility.data.remote

import com.nikhilchauhan.housefacility.data.remote.api.ApiService
import javax.inject.Inject

class RemoteDataSource @Inject constructor(private val apiService: ApiService): BaseApiResponse() {

  suspend fun getHomeFacilities() = safeApiCall {
    apiService.getHomeFacilities()
  }
}