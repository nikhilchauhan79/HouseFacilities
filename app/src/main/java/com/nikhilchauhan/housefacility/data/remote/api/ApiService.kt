package com.nikhilchauhan.housefacility.data.remote.api

import com.nikhilchauhan.housefacility.constants.Constants
import com.nikhilchauhan.housefacility.data.remote.model.HomeResponse
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

  @GET(Constants.HOME_FACILITIES_URL)
  suspend fun getHomeFacilities() : Response<HomeResponse>
}