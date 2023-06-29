package com.nikhilchauhan.housefacility.ui.screens

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.nikhilchauhan.housefacility.data.local.entities.HomeEntity
import com.nikhilchauhan.housefacility.ui.components.RadioOptions
import com.nikhilchauhan.housefacility.ui.viewmodels.HomeViewModel

@Composable
fun FacilitiesScreen(
  facilityList: List<HomeEntity.Facility>,
  viewModel: HomeViewModel = hiltViewModel(),
) {
  val homeUiState = viewModel.homeUiState.collectAsStateWithLifecycle().value
  LazyColumn {

    items(facilityList) { facility ->
      val nnOptions = facility.options?.filterNotNull() ?: emptyList()
      FacilityRow(
        facility = facility,
        nnOptions,
        selectedOption = homeUiState[facility.facilityId]?.selectedOption ?: nnOptions[0],
        onOptionSelected = homeUiState[facility.facilityId]?.onOptionSelected
          ?: { facilityId, option ->
          }
      )
    }
  }
}

@Composable
fun FacilityRow(
  facility: HomeEntity.Facility, options: List<HomeEntity.Facility.Option?>,
  selectedOption: HomeEntity.Facility.Option,
  onOptionSelected: (facilityId: String, HomeEntity.Facility.Option) -> Unit
) {
  Row(
    modifier = Modifier
      .fillMaxWidth()
      .wrapContentHeight(),
  ) {
    Text(
      text = facility.name ?: "",
      style = MaterialTheme.typography.bodyLarge,
      modifier = Modifier
        .width(120.dp)
        .padding(top = 24.dp),
      maxLines = 3
    )
    RadioOptions(options = options, selectedOption = selectedOption, onOptionSelected = {
      onOptionSelected(facility.facilityId ?: "", it)
    })
  }
}