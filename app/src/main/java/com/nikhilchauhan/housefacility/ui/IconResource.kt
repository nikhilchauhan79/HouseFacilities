package com.nikhilchauhan.housefacility.ui

import androidx.annotation.DrawableRes
import com.nikhilchauhan.housefacility.R

sealed class IconResource(
  @DrawableRes
  val drawableId: Int,
  val optionId: String
) {
  class Apartment : IconResource(R.drawable.apartment, "1")
  class Condo : IconResource(R.drawable.condo, "2")
  class BoatHouse : IconResource(R.drawable.boat, "3")
  class Land : IconResource(R.drawable.land, "4")
  class Rooms : IconResource(R.drawable.rooms, "6")
  class NoRooms : IconResource(R.drawable.no_room, "7")
  class SwimmingPool : IconResource(R.drawable.swimming, "10")
  class GardenArea : IconResource(R.drawable.garden, "11")
  class Garage : IconResource(R.drawable.garage, "12")
}