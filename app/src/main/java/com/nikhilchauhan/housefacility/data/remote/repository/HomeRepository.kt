package com.nikhilchauhan.housefacility.data.remote.repository

import com.nikhilchauhan.housefacility.data.helpers.performGetOperation
import com.nikhilchauhan.housefacility.data.local.dao.HomeDao
import com.nikhilchauhan.housefacility.data.local.entities.HomeEntity
import com.nikhilchauhan.housefacility.data.remote.RemoteDataSource
import com.nikhilchauhan.housefacility.data.remote.model.toRoomEntity
import javax.inject.Inject

class HomeRepository @Inject constructor(
  private val remoteDataSource: RemoteDataSource,
  private val localDataSource: HomeDao
) {

  fun getHomeFacilities() =
    performGetOperation(databaseQuery = { localDataSource.getHomeFacilities() },
      networkCall = { remoteDataSource.getHomeFacilities() },
      saveCallResult = {
        localDataSource.insertHomeFacility(it.toRoomEntity())
      })
}