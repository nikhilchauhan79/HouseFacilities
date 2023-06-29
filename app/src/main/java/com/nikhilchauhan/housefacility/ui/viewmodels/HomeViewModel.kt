package com.nikhilchauhan.housefacility.ui.viewmodels

import android.util.Log
import androidx.compose.runtime.mutableStateMapOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nikhilchauhan.housefacility.data.local.entities.HomeEntity
import com.nikhilchauhan.housefacility.data.remote.NetworkResult
import com.nikhilchauhan.housefacility.data.remote.repository.HomeRepository
import com.nikhilchauhan.housefacility.ui.HomeUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
  private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
  private val homeRepository: HomeRepository
) : ViewModel() {
  private val _homeNetworkResult: MutableStateFlow<NetworkResult<HomeEntity>?> =
    MutableStateFlow(null)
  val homeNetworkResult: StateFlow<NetworkResult<HomeEntity>?> = _homeNetworkResult.asStateFlow()
  val exclusionsList = mutableListOf<MutableMap<String, String>>()

  val homeUiState = MutableStateFlow(
    mutableStateMapOf<String, HomeUiState>()
  )

  init {
    viewModelScope.launch {
      fetchHomeFacilities()
      joinAll()
    }
  }

  private fun buildHomeUiState(data: HomeEntity) {
    buildExclusionsMappedList(data)

    data.facilities?.filterNotNull()?.forEach { facility ->
      facility.options?.get(0)?.let { option ->
        homeUiState.value[facility.facilityId.toString()] =
          HomeUiState(option) { facilityId, optionSelected ->
            homeUiState.update { map ->
              val newState = map[facilityId]?.copy(selectedOption = optionSelected)
              if (newState != null) {
                map[facilityId] = newState
              }
              map
            }
            validateState()
          }
      }
    }
  }

  private fun buildExclusionsMappedList(data: HomeEntity) {
    val exclusions = data.exclusions?.filterNotNull() ?: emptyList()
    exclusions.forEach { list ->
      val map = mutableMapOf<String, String>()
      list.map {
        map[it?.facilityId ?: ""] = it?.optionsId ?: ""
      }
      exclusionsList.add(map)
    }
  }

  private fun validateState(): MutableMap<String, String> {
    val hasExclusions = mutableListOf(mutableMapOf<String, String>())
    homeUiState.value.forEach { (facilityId, state) ->
      val currentMap = mutableMapOf<String, String>()
      exclusionsList.forEach { map ->
        currentMap.putAll(map.filter {
          it.key == facilityId && it.value == state.selectedOption.id
        })
      }
      hasExclusions.add(currentMap)
    }
    val exclusionMap = mutableMapOf<String, String>()
    hasExclusions.forEach {
      if (it.isNotEmpty()) {
        exclusionMap.putAll(it)
      }
    }
    var answerMap = mutableMapOf<String, String>()
    exclusionsList.forEach { exMapList ->
      answerMap = mutableMapOf()
      exclusionMap.forEach {exMap ->
        if(exMapList.containsKey(exMap.key) && exMapList.containsValue(exMap.value)){
          answerMap[exMap.key] = exMap.value
        }
      }
      if(answerMap.size == exMapList.size && exclusionsList.contains(answerMap)){
        Log.d("TAG", "validateState: $answerMap")
        return answerMap
      }
    }
    if (exclusionsList.contains(answerMap)) {
      Log.d("TAG", "validateState: $exclusionMap")
    }
    return answerMap
  }


  private fun fetchHomeFacilities() {
    viewModelScope.launch {
      homeRepository.getHomeFacilities().collect { result ->
        _homeNetworkResult.emit(result)
        if (result is NetworkResult.Success) {
          if (result.data != null) {
            buildHomeUiState(result.data)
          }
        }
      }
    }
  }
}