package com.nikhilchauhan.housefacility.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.nikhilchauhan.housefacility.data.local.dao.HomeDao
import com.nikhilchauhan.housefacility.data.local.entities.HomeEntity

@Database(entities = [HomeEntity::class], version = 1, exportSchema = false)
abstract class HomeDatabase : RoomDatabase() {
  abstract fun homeDao(): HomeDao

  companion object {
    @Volatile
    private var instance: HomeDatabase? = null

    fun getDatabase(context: Context): HomeDatabase = instance ?: synchronized(this) {
      instance ?: buildDatabase(context).also {
        instance = it
      }
    }

    private fun buildDatabase(appContext: Context): HomeDatabase =
      Room.databaseBuilder(appContext, HomeDatabase::class.java, "home.db")
        .build()
  }
}