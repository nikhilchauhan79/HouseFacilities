package com.nikhilchauhan.housefacility.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nikhilchauhan.housefacility.data.local.entities.HomeEntity

@Composable
fun FacilityRow(
  facility: HomeEntity.Facility, options: List<HomeEntity.Facility.Option?>,
  selectedOption: HomeEntity.Facility.Option,
  onOptionSelected: (facilityId: String, HomeEntity.Facility.Option) -> Unit
) {
  Card(
    modifier = Modifier.padding(horizontal = 8.dp, vertical = 12.dp)
  ) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .wrapContentHeight(),
    ) {
      Text(
        text = facility.name ?: "",
        style = MaterialTheme.typography.headlineSmall.copy(fontSize = 22.sp),
        modifier = Modifier
          .width(120.dp)
          .padding(top = 24.dp, end = 8.dp, start = 12.dp),
        maxLines = 3
      )
      RadioOptions(options = options, selectedOption = selectedOption, onOptionSelected = {
        onOptionSelected(facility.facilityId ?: "", it)
      })
    }
  }
}