package com.nikhilchauhan.housefacility.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.nikhilchauhan.housefacility.data.local.entities.HomeEntity

@Composable
fun RadioOptions(
  options: List<HomeEntity.Facility.Option?>,
  selectedOption: HomeEntity.Facility.Option,
  onOptionSelected: (HomeEntity.Facility.Option) -> Unit
) {
  Column(modifier = Modifier.fillMaxWidth()) {
    options.forEach { optionItem ->
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .selectable(selected = optionItem?.id == selectedOption.id, onClick = {
            if (optionItem != null) {
              onOptionSelected(optionItem)
            }
          })
          .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Text(text = optionItem?.name.toString(), modifier = Modifier.weight(0.5f))
        RadioButton(selected = optionItem?.id == selectedOption.id, onClick = {
          if (optionItem != null) {
            onOptionSelected(optionItem)
          }
        }, modifier = Modifier.weight(0.5f).padding(0.dp))
      }
    }
  }
}