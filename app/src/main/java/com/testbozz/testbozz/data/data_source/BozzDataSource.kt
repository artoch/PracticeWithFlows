package com.testbozz.testbozz.data.data_source

import android.util.Log
import com.testbozz.testbozz.data.api.BozzApi
import com.testbozz.testbozz.utils.NetworkState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import java.lang.Exception
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BozzDataSource @Inject constructor(private val bozzApi: BozzApi){

    //Para gestionar el estado de conexión
    val _networkStateFlow = MutableStateFlow(NetworkState.LOADED)

    fun getAllPostFlow() = flow {
        try {
            _networkStateFlow.value = NetworkState.LOADING
            emit(bozzApi.getAllPost())
        }catch (e: Exception){
            Log.e("Network", "ERROR LIST POST")
            _networkStateFlow.value = NetworkState.ERROR_LIST
        }
    }.flowOn(Dispatchers.IO)

    fun getPostFlow(id:Int) = flow {
        try {
            _networkStateFlow.value = NetworkState.LOADING
            emit(bozzApi.getPost(id))
        }catch (e: Exception){
            Log.e("Network", "ERROR LIST POST BY ID")
            _networkStateFlow.value = NetworkState.ERROR_POST
        }
    }.flowOn(Dispatchers.IO)

    fun getPostCommentsFlow(id:Int) = flow {
        try {
            _networkStateFlow.value = NetworkState.LOADING
            emit(bozzApi.getCommentsPost(id))
        }catch (e: Exception) {
            Log.e("Network", "ERROR LIST POST COMMENTS")
            _networkStateFlow.value = NetworkState.ERROR_COMMENT
        }
    }.flowOn(Dispatchers.IO)

}