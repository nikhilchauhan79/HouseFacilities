package com.nikhilchauhan.housefacility

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.android.material.snackbar.Snackbar
import com.nikhilchauhan.housefacility.data.local.entities.HomeEntity
import com.nikhilchauhan.housefacility.data.remote.NetworkResult
import com.nikhilchauhan.housefacility.ui.screens.FacilitiesScreen
import com.nikhilchauhan.housefacility.ui.theme.HouseFacilityTheme
import com.nikhilchauhan.housefacility.ui.viewmodels.HomeViewModel
import com.nikhilchauhan.housefacility.ui.viewmodels.UiStates
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch

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