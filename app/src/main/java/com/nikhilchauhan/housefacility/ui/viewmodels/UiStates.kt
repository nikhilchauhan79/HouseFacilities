package com.nikhilchauhan.housefacility.ui.viewmodels


sealed class UiStates<T>(
  val data: T? = null,
  val errorMessage: String? = null
) {
  class Invalid<T>(data: T?, errorMessage: String?) : UiStates<T>(data, errorMessage)
  class Valid<T>(data: T?) : UiStates<T>(data)
  class Initialised<T>(data: T?) : UiStates<T>(data)
}