package com.testbozz.testbozz.data.cache.repository

import com.testbozz.testbozz.data.cache.dao.PostDao
import com.testbozz.testbozz.data.models.PostItemEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PostCacheRepository @Inject constructor(private val postDao: PostDao){

    fun getAllLocalPost(): Flow<List<PostItemEntity>> =
        postDao.getAllLocalPost().map {
            it
        }

    fun getPostById(id:Int): Flow<PostItemEntity> = postDao.getPost(id).map {
        it
    }

    suspend fun addPost(postItemEntity: PostItemEntity) {
        postDao.addPost(postItemEntity)
    }

    suspend fun deletePost(id: Int) {
        postDao.deletePost(id)
    }
}