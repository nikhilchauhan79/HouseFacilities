package com.nikhilchauhan.housefacility.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.nikhilchauhan.housefacility.data.local.entities.HomeEntity
import com.nikhilchauhan.housefacility.ui.IconResource
import com.nikhilchauhan.housefacility.ui.components.FacilityRow
import com.nikhilchauhan.housefacility.ui.components.RadioOptions
import com.nikhilchauhan.housefacility.ui.viewmodels.HomeViewModel
import com.nikhilchauhan.housefacility.ui.viewmodels.UiStates
import kotlinx.coroutines.CoroutineScope

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun FacilitiesScreen(
  facilityList: List<HomeEntity.Facility>,
  viewModel: HomeViewModel = hiltViewModel(),
) {
  val homeUiState = viewModel.homeUiState.collectAsStateWithLifecycle()
  val uiStates =
    viewModel.errorStates.collectAsStateWithLifecycle(
      initialValue = UiStates.Initialised(
        mutableListOf()
      )
    )
  val snackBarHostState = remember {
    SnackbarHostState()
  }
  val showSnackbar = remember {
    mutableStateOf(false)
  }


  Scaffold(
    topBar = {
      TopAppBar(title = {
        Text(text = "Home Facilities", style = MaterialTheme.typography.headlineMedium)
      }, navigationIcon = {
        IconButton(onClick = { }) {
          Icon(Icons.Default.Menu, contentDescription = "Menu Icon")
        }
      })
    },
    snackbarHost = { SnackbarHost(hostState = snackBarHostState) },

    ) {
    LazyColumn(contentPadding = it) {

      items(facilityList) { facility ->
        val nnOptions = facility.options?.filterNotNull() ?: emptyList()
        FacilityRow(
          facility = facility,
          nnOptions,
          selectedOption = homeUiState.value[facility.facilityId]?.selectedOption ?: nnOptions[0],
          onOptionSelected = homeUiState.value[facility.facilityId]?.onOptionSelected
            ?: { facilityId, option ->
            }
        )
      }
    }
  }
  HandleUiStates(
    uiStates = uiStates.value,
    snackBarHostState = snackBarHostState,
    showSnackbar = showSnackbar.value,
    setShowSnackbar = {
      showSnackbar.value = it
    })
}

@Composable
private fun HandleUiStates(
  uiStates: UiStates<MutableList<HomeEntity.Facility.Option>>,
  snackBarHostState: SnackbarHostState,
  showSnackbar: Boolean,
  setShowSnackbar: (Boolean) -> Unit
) {
  when (uiStates) {
    is UiStates.Initialised -> {

    }

    is UiStates.Invalid -> {
      setShowSnackbar(true)
      LaunchedEffect(key1 = uiStates) {
        if (showSnackbar) {
          val snackbarResult = snackBarHostState.showSnackbar(
            "${uiStates.errorMessage} cannot be selected together. Please select correct options.",
            duration = SnackbarDuration.Short
          )
          when (snackbarResult) {
            SnackbarResult.Dismissed -> {
              setShowSnackbar(false)
            }

            SnackbarResult.ActionPerformed -> {

            }
          }
        }
      }
    }

    is UiStates.Valid -> {

    }
  }
}

fun getIconResource(optionId: String): IconResource {
  return when (optionId) {
    "1" -> IconResource.Apartment()
    "2" -> IconResource.Condo()
    "3" -> IconResource.BoatHouse()
    "4" -> IconResource.Land()
    "6" -> IconResource.Rooms()
    "7" -> IconResource.NoRooms()
    "10" -> IconResource.SwimmingPool()
    "11" -> IconResource.GardenArea()
    "12" -> IconResource.Garage()
    else -> {
      IconResource.Apartment()
    }
  }
}