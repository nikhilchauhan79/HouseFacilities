package com.nikhilchauhan.housefacility.data.remote.model


import com.google.gson.annotations.SerializedName
import com.nikhilchauhan.housefacility.data.local.entities.HomeEntity

data class HomeResponse(
  @SerializedName("exclusions")
  val exclusions: List<List<Exclusion?>?>?,
  @SerializedName("facilities")
  val facilities: List<Facility?>?
) {
  data class Exclusion(
    @SerializedName("facility_id")
    val facilityId: String?,
    @SerializedName("options_id")
    val optionsId: String?
  )

  data class Facility(
    @SerializedName("facility_id")
    val facilityId: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("options")
    val options: List<Option?>?
  ) {
    data class Option(
      @SerializedName("icon")
      val icon: String?,
      @SerializedName("id")
      val id: String?,
      @SerializedName("name")
      val name: String?
    )
  }
}

fun HomeResponse.toRoomEntity() = HomeEntity(0, exclusions?.map {
  it?.toRoomEntity()
}, facilities?.map { it?.toRoomEntity() })

fun List<HomeResponse.Exclusion?>.toRoomEntity() = map {
  HomeEntity.Exclusion(it?.facilityId, it?.optionsId)
}.toList()

fun HomeResponse.Facility.toRoomEntity() = HomeEntity.Facility(facilityId, name, options?.map {
  it?.toRoomEntity()
})

fun HomeResponse.Facility.Option.toRoomEntity() = HomeEntity.Facility.Option(icon, id, name)
