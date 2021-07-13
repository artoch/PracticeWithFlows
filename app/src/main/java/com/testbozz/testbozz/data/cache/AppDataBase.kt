package com.testbozz.testbozz.data.cache

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.testbozz.testbozz.data.cache.dao.PostDao
import com.testbozz.testbozz.data.models.PostItemEntity

@Database(entities = [PostItemEntity::class], version = 1, exportSchema = false)
abstract class AppDataBase: RoomDatabase() {
    abstract fun  getCompaniesDao(): PostDao
}