package com.nikhilchauhan.housefacility.data.helpers

import com.nikhilchauhan.housefacility.data.local.entities.HomeEntity
import com.nikhilchauhan.housefacility.data.remote.model.HomeResponse
import com.nikhilchauhan.housefacility.data.remote.model.toRoomEntity

object DataMapper {
  inline fun <reified T, reified A> A.networkResponseToRoomEntity(): T? {
    return when {
      T::class.java == HomeEntity::class.java
          && A::class.java == HomeResponse::class.java -> (this as HomeResponse).toRoomEntity() as T

      else -> null
    }
  }
}