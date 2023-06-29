package com.nikhilchauhan.housefacility.ui

import com.nikhilchauhan.housefacility.data.local.entities.HomeEntity

data class HomeUiState(
  var selectedOption: HomeEntity.Facility.Option,
  val onOptionSelected: (facilityId: String, HomeEntity.Facility.Option) -> Unit
)