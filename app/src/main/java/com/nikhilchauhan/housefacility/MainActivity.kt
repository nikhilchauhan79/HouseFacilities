package com.nikhilchauhan.housefacility

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.nikhilchauhan.housefacility.data.local.entities.HomeEntity
import com.nikhilchauhan.housefacility.data.remote.NetworkResult
import com.nikhilchauhan.housefacility.ui.screens.FacilitiesScreen
import com.nikhilchauhan.housefacility.ui.theme.HouseFacilityTheme
import com.nikhilchauhan.housefacility.ui.viewmodels.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    setContent {
      val homeViewModel: HomeViewModel = hiltViewModel()
      val facilitiesNetworkResult = homeViewModel.homeNetworkResult.collectAsStateWithLifecycle()
      HouseFacilityTheme {
        // A surface container using the 'background' color from the theme
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
          facilitiesNetworkResult.value?.let {
            HandleFacilitiesNetworkResult(it)
          }
        }
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