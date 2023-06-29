package com.nikhilchauhan.housefacility.data.local

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.nikhilchauhan.housefacility.data.local.entities.HomeEntity

object Convertors {

  @TypeConverter
  fun fromExclusionsList(value: List<List<HomeEntity.Exclusion?>?>?): String {
    val type = object : TypeToken<List<List<HomeEntity.Exclusion?>?>?>() {}.type
    return Gson().toJson(value, type)
  }

  @TypeConverter
  fun toExclusionsList(value: String): List<List<HomeEntity.Exclusion?>?>? {
    val gson = Gson()
    val type = object : TypeToken<List<List<HomeEntity.Exclusion?>?>?>() {}.type
    return gson.fromJson(value, type)
  }

  @TypeConverter
  fun fromFacilitiesList(value: List<HomeEntity.Facility?>?): String {
    val type = object : TypeToken<List<HomeEntity.Facility?>?>() {}.type
    return Gson().toJson(value, type)
  }

  @TypeConverter
  fun toFacilitiesList(value: String): List<HomeEntity.Facility?>? {
    val gson = Gson()
    val type = object : TypeToken<List<HomeEntity.Facility?>?>() {}.type
    return gson.fromJson(value, type)
  }
}