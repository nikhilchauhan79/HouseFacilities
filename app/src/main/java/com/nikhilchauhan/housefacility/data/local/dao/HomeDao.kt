package com.nikhilchauhan.housefacility.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nikhilchauhan.housefacility.data.local.entities.HomeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface HomeDao {
  @Query("SELECT * FROM home_facilities")
  fun getHomeFacilities(): Flow<HomeEntity>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  fun insertHomeFacility(homeEntity: HomeEntity)
}