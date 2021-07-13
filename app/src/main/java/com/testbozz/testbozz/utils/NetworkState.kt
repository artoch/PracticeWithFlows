package com.testbozz.testbozz.utils

enum class Status{
    RUNNING,
    SUCCESS,
    FAILED,
    END_OF_LIST
}

class NetworkState(val status:Status, val msg:StateForFragments) {

    companion object{
        val LOADED:NetworkState             = NetworkState(Status.SUCCESS,StateForFragments.Success)
        val LOADING:NetworkState            = NetworkState(Status.RUNNING,StateForFragments.Loading)
        val ERROR_LIST:NetworkState = NetworkState(Status.FAILED,StateForFragments.ErrorListFragment)
        val ERROR_POST:NetworkState         = NetworkState(Status.FAILED,StateForFragments.ErrorPostIdFragment)
        val ERROR_COMMENT:NetworkState      = NetworkState(Status.FAILED,StateForFragments.ErrorPostComents)

    }
}