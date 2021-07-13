package com.testbozz.testbozz.di

import android.content.Context
import androidx.room.Room
import com.testbozz.testbozz.data.cache.AppDataBase
import com.testbozz.testbozz.data.cache.dao.PostDao
import com.testbozz.testbozz.utils.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class )
object RoomModule {
    @Singleton
    @Provides
    fun appDatabase(@ApplicationContext context: Context): AppDataBase {
        return Room.databaseBuilder(context, AppDataBase::class.java, DATABASE_NAME)
            .build()
    }

    @Singleton
    @Provides
    fun postDao(appDatabase: AppDataBase): PostDao {
        return appDatabase.getCompaniesDao()
    }
}