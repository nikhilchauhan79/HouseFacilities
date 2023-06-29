package com.nikhilchauhan.housefacility.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "home_facilities")
data class HomeEntity(
  @ColumnInfo(name = "id")
  @PrimaryKey(autoGenerate = true)
  val id: Int,
  @ColumnInfo(name = "exclusions")
  val exclusions: List<List<HomeEntity.Exclusion?>?>?,
  @ColumnInfo(name = "facilities")
  val facilities: List<HomeEntity.Facility?>?,
  @ColumnInfo(name = "updated_at")
  val updatedAt: Long = System.currentTimeMillis()
) {
  data class Exclusion(
    @ColumnInfo(name = "facility_id")
    val facilityId: String?,
    @ColumnInfo(name = "options_id")
    val optionsId: String?
  )

  data class Facility(
    @ColumnInfo(name = "facility_id")
    val facilityId: String?,
    @ColumnInfo(name = "name")
    val name: String?,
    @ColumnInfo(name = "options")
    val options: List<Option?>?
  ) {
    data class Option(
      @ColumnInfo(name = "icon")
      val icon: String?,
      @ColumnInfo(name = "id")
      val id: String?,
      @ColumnInfo(name = "name")
      val name: String?
    )
  }
}