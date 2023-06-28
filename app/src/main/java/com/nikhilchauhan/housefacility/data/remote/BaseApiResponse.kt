package com.nikhilchauhan.housefacility.data.remote

import android.util.Log
import retrofit2.Response
import java.lang.Exception

abstract class BaseApiResponse {
  suspend fun <T> safeApiCall(apiCall: suspend () -> Response<T>): NetworkResult<T> {
    try {
      val response = apiCall()
      if (response.isSuccessful) {
        val body = response.body()
        body?.let { nnBody ->
          return NetworkResult.Success(nnBody)
        }
      }
      return error("${response.code()} ${response.message()} ${response.errorBody()}")
    } catch (e: Exception) {
      Log.d("BaseApiResponse", "safeApiCall: $e")
      return error(e.message ?: e.toString())
    }
  }

  private fun <T> error(errorMessage: String): NetworkResult<T> = NetworkResult.Error(errorMessage)
}