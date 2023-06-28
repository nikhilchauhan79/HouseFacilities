package com.nikhilchauhan.housefacility.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nikhilchauhan.housefacility.data.local.entities.HomeEntity
import com.nikhilchauhan.housefacility.data.remote.NetworkResult
import com.nikhilchauhan.housefacility.data.remote.repository.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
  private val homeRepository: HomeRepository
) : ViewModel() {
  private val _homeNetworkResult: MutableStateFlow<NetworkResult<HomeEntity>?> =
    MutableStateFlow(null)
  val homeNetworkResult: StateFlow<NetworkResult<HomeEntity>?> = _homeNetworkResult.asStateFlow()

  fun fetchHomeFacilities() {
    viewModelScope.launch {
      homeRepository.getHomeFacilities().collect { result ->
        _homeNetworkResult.emit(result)
      }
    }
  }
}