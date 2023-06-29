package com.nikhilchauhan.housefacility

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.nikhilchauhan.housefacility.data.local.entities.HomeEntity
import com.nikhilchauhan.housefacility.data.remote.NetworkResult
import com.nikhilchauhan.housefacility.ui.screens.FacilitiesScreen
import com.nikhilchauhan.housefacility.ui.theme.HouseFacilityTheme
import com.nikhilchauhan.housefacility.ui.viewmodels.HomeViewModel
import com.nikhilchauhan.housefacility.ui.viewmodels.UiStates
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    setContent {
      val homeViewModel: HomeViewModel = hiltViewModel()
      val facilitiesNetworkResult = homeViewModel.homeNetworkResult.collectAsStateWithLifecycle()
      val uiStates =
        homeViewModel.errorStates.collectAsStateWithLifecycle(
          initialValue = UiStates.Initialised(
            mutableListOf()
          )
        )

      HouseFacilityTheme {
        // A surface container using the 'background' color from the theme
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
          facilitiesNetworkResult.value?.let {
            HandleFacilitiesNetworkResult(it)
            HandleUiStates(uiStates.value)
          }
        }
      }
    }
  }

  @Composable
  private fun HandleUiStates(uiStates: UiStates<MutableList<HomeEntity.Facility.Option>>) {
    val context = LocalContext.current
    when (uiStates) {
      is UiStates.Initialised -> {

      }

      is UiStates.Invalid -> {
        Toast.makeText(
          context,
          "${uiStates.errorMessage} cannot be selected together. Please select correct options.",
          Toast.LENGTH_SHORT
        ).show()
      }

      is UiStates.Valid -> {

      }
    }
  }

  @Composable
  private fun HandleFacilitiesNetworkResult(facilitiesNetworkResult: NetworkResult<HomeEntity>) {
    when (facilitiesNetworkResult) {
      is NetworkResult.Error -> {
        Column(
          horizontalAlignment = Alignment.CenterHorizontally,
          verticalArrangement = Arrangement.Center,
          modifier = Modifier.fillMaxSize()
        ) {
          Text(facilitiesNetworkResult.message ?: "")
        }
      }

      is NetworkResult.Loading -> {
        Column(
          horizontalAlignment = Alignment.CenterHorizontally,
          verticalArrangement = Arrangement.Center,
          modifier = Modifier.fillMaxSize()
        ) {
          CircularProgressIndicator()
        }
      }

      is NetworkResult.Success -> {
        FacilitiesScreen(
          facilityList = facilitiesNetworkResult.data?.facilities?.filterNotNull() ?: emptyList(),
        )
      }
    }

  }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
  Text(
    text = "Hello $name!",
    modifier = modifier
  )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
  HouseFacilityTheme {
    Greeting("Android")
  }
}