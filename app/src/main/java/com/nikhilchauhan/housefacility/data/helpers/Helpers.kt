package com.nikhilchauhan.housefacility.data.helpers

import com.nikhilchauhan.housefacility.data.helpers.DataMapper.networkResponseToRoomEntity
import com.nikhilchauhan.housefacility.data.local.entities.HomeEntity
import com.nikhilchauhan.housefacility.data.remote.NetworkResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.time.LocalDate
import java.time.ZoneId
import java.util.Date


inline fun <reified T, reified A> performGetOperation(
  noinline databaseQuery: () -> Flow<T>,
  noinline networkCall: suspend () -> NetworkResult<A>,
  noinline saveCallResult: suspend (A) -> Unit
): Flow<NetworkResult<T>> =
  flow<NetworkResult<T>> {
    emit(NetworkResult.Loading())
    val localData = databaseQuery.invoke().first()
    val localResult = NetworkResult.Success(localData)
    emit(localResult)
    val shouldUpdateFromServer = if(localResult.data != null) {
        shouldUpdateFromServer((localResult.data as? HomeEntity)?.updatedAt)
    } else true

    if (shouldUpdateFromServer) {
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
    }
  }.flowOn(Dispatchers.IO)

fun shouldUpdateFromServer(updatedAt: Long?): Boolean {
  if (updatedAt == null) return true
  val localDate: LocalDate =
    Date(updatedAt).toInstant().atZone(ZoneId.systemDefault()).toLocalDate().plusDays(1)
  val today = LocalDate.now()
  return today.isAfter(localDate)
}