package com.nikhilchauhan.housefacility.di.module

import android.content.Context
import com.nikhilchauhan.housefacility.data.local.HomeDatabase
import com.nikhilchauhan.housefacility.data.local.dao.HomeDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

  @Provides
  @Singleton
  fun provideHomeDao(@ApplicationContext context: Context): HomeDao =
    HomeDatabase.getDatabase(context).homeDao()

  @Provides
  @Singleton
  fun provideIoDispatchers(): CoroutineDispatcher = Dispatchers.IO
}