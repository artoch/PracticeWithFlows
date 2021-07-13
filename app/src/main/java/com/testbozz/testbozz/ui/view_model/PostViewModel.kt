package com.testbozz.testbozz.ui.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.testbozz.testbozz.data.cache.repository.PostCacheRepository
import com.testbozz.testbozz.data.models.CommentsPostEntityItem
import com.testbozz.testbozz.data.models.PostItemEntity
import com.testbozz.testbozz.repository.PostRepository
import com.testbozz.testbozz.utils.NetworkState
import com.testbozz.testbozz.utils.StateForFragments
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class PostViewModel @Inject constructor(private val postRepository: PostRepository, private val postCacheRepository: PostCacheRepository) : ViewModel(){

    private val _networkState = MutableLiveData<StateForFragments>()
    val networkState: LiveData<StateForFragments>
            get() = _networkState

    private val _allMyPost = MutableLiveData<List<PostItemEntity>>()
    val allMyPost: LiveData<List<PostItemEntity>>
        get () = _allMyPost

    private val _myPost = MutableLiveData<PostItemEntity?>()
    val myPost: LiveData<PostItemEntity?>
        get () = _myPost

    private val _myPostComments = MutableLiveData<List<CommentsPostEntityItem>>()
    val myPostComments: LiveData<List<CommentsPostEntityItem>>
        get () = _myPostComments

    private val networkStateFlow: StateFlow<NetworkState>
        get () = postRepository.getNetworkStateFlow()

    fun clearData(){
        _allMyPost.postValue(ArrayList())
        _myPost.postValue(null)
        _myPostComments.postValue(ArrayList())

    }

    // ----------------------------------------         NETWORKSTATE  -----------------------------------

    fun networkStateFlow(){
        viewModelScope.launch {
            networkStateFlow.collect {
                when (it){
                    NetworkState.LOADED ->  _networkState.postValue(StateForFragments.Success)
                    NetworkState.LOADING -> _networkState.postValue(StateForFragments.Loading)
                    NetworkState.ERROR_LIST -> _networkState.postValue(StateForFragments.ErrorListFragment)
                    NetworkState.ERROR_POST -> _networkState.postValue(StateForFragments.ErrorPostIdFragment)
                    NetworkState.ERROR_COMMENT -> _networkState.postValue(StateForFragments.ErrorPostComents)
                }
            }
        }
    }

    // ----------------------------------------         NETWORK DATA -----------------------------------

    fun getAllPostFlow(){
        viewModelScope.launch {
            postRepository.getAllPostFlow().collect {
                if (it.isSuccessful) {//REMOTE
                    postRepository.getNetworkStateFlow().value = NetworkState.LOADED
                    _allMyPost.postValue(it.body())
                } else {//Local
                    postRepository.getNetworkStateFlow().value = NetworkState.ERROR_LIST
                }
            }
        }
    }


    fun getPostByIdFlow(id:Int){
        viewModelScope.launch {
            postRepository.getPostById(id).collect {
                if (it.isSuccessful){//REMOTE
                    postRepository.getNetworkStateFlow().value = NetworkState.LOADED
                    _myPost.postValue(it.body())
                }else{//Local
                    postRepository.getNetworkStateFlow().value = NetworkState.ERROR_POST
                }
            }
        }
    }

    fun getPostCommentsByIdFlow(id:Int){
        viewModelScope.launch {
            postRepository.getPostCommentsById(id).collect {
                if (it.isSuccessful){//REMOTE
                    postRepository.getNetworkStateFlow().value = NetworkState.LOADED
                    _myPostComments.postValue(it.body())
                }else{//Local
                    postRepository.getNetworkStateFlow().value = NetworkState.ERROR_COMMENT

                }
            }
        }
    }


    // ----------------------------------------         CACHE DATA -----------------------------------

    fun getMyCachePost(){
        viewModelScope.launch {
            postCacheRepository.getAllLocalPost().collect { data ->
                if (_networkState.value is  StateForFragments.ErrorListFragment)
                    _allMyPost.postValue(data)
            }
        }
    }

    fun getMyCachePostById(id:Int){
        viewModelScope.launch {
            postCacheRepository.getPostById(id).collect { data ->
                if (_networkState.value is StateForFragments.ErrorPostIdFragment)
                    _myPost.postValue(data)
            }
        }
    }

    fun saveToCachePost(postItemEntity: PostItemEntity){
        viewModelScope.launch {
            postCacheRepository.addPost(postItemEntity)
        }
    }

    fun deleteFromCachePost(id: Int){
        viewModelScope.launch {
            postCacheRepository.deletePost(id)
        }
    }
}