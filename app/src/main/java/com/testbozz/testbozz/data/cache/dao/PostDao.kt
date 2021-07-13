package com.testbozz.testbozz.data.cache.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.testbozz.testbozz.data.models.PostItemEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PostDao {

    @Query("SELECT * FROM post_table")
    fun getAllLocalPost(): Flow<List<PostItemEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addPost(postItemEntity: PostItemEntity)

    @Query("DELETE FROM post_table WHERE :id = id")
    suspend fun deletePost(id:Int)

    @Query("SELECT * FROM post_table WHERE :id = id")
    fun getPost(id:Int): Flow<PostItemEntity>
}