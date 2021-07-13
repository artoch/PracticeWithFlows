package com.testbozz.testbozz.data.api

import com.testbozz.testbozz.data.models.CommentsPostEntityItem
import com.testbozz.testbozz.data.models.PostItemEntity
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface BozzApi {

    @GET("posts/")
    suspend fun getAllPost(): Response<PostItemEntity.PostItemResponse>

    @GET("posts/{id}/")
    suspend fun getPost(@Path("id") id:Int): Response<PostItemEntity>

    @GET("posts/{id}/comments/")
    suspend fun getCommentsPost(@Path("id") id:Int): Response<CommentsPostEntityItem.CommentsPostEntity>
}