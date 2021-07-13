package com.testbozz.testbozz.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.testbozz.testbozz.data.data_source.BozzDataSource
import com.testbozz.testbozz.data.models.CommentsPostEntityItem
import com.testbozz.testbozz.data.models.PostItemEntity
import com.testbozz.testbozz.utils.NetworkState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PostRepository @Inject constructor(private val bozzDataSource: BozzDataSource){

    fun getNetworkStateFlow() = bozzDataSource._networkStateFlow

    fun getAllPostFlow()  = bozzDataSource.getAllPostFlow().map {
        it
    }

    fun getPostById(id:Int) = bozzDataSource.getPostFlow(id).map {
        it
    }

    fun getPostCommentsById(id:Int) = bozzDataSource.getPostCommentsFlow(id).map {
        it
    }


}