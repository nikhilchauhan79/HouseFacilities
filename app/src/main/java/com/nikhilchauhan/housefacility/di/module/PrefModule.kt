package com.nikhilchauhan.housefacility.di.module

import com.nikhilchauhan.housefacility.data.datastore.PrefDataStoreRepo
import com.nikhilchauhan.housefacility.data.datastore.PrefDataStoreRepoImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class PrefModule {

  @Singleton
  @Binds
  abstract fun bindsPreferencesRepo(prefDataStoreRepoImpl: PrefDataStoreRepoImpl): PrefDataStoreRepo
}