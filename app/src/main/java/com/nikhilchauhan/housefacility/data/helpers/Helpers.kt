package com.nikhilchauhan.housefacility.data.helpers

import com.nikhilchauhan.housefacility.data.helpers.DataMapper.networkResponseToRoomEntity
import com.nikhilchauhan.housefacility.data.local.entities.HomeEntity
import com.nikhilchauhan.housefacility.data.remote.NetworkResult
import com.nikhilchauhan.housefacility.data.remote.model.HomeResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

inline fun <reified T, reified A> performGetOperation(
  noinline databaseQuery: () -> Flow<T>,
  noinline networkCall: suspend () -> NetworkResult<A>,
  noinline saveCallResult: suspend (A) -> Unit
): Flow<NetworkResult<T>> =
  flow<NetworkResult<T>> {
    emit(NetworkResult.Loading())
    val localData = databaseQuery.invoke().first()
    emit(NetworkResult.Success(localData))

    val networkResponse = networkCall.invoke()

    if (networkResponse is NetworkResult.Success) {
      networkResponse.data?.let { nnData ->
        saveCallResult(nnData)
        (nnData as A).networkResponseToRoomEntity<T, A>()?.let {
          emit(NetworkResult.Success(it))
        }
      }
    } else if (networkResponse is NetworkResult.Error) {
      networkResponse.message?.let {
        emit(NetworkResult.Error(it))
      }
    }
  }.flowOn(Dispatchers.IO)