package com.testbozz.testbozz.data.models


import com.google.gson.annotations.SerializedName

data class ResponsePostItem(
    val body: String,
    val id: Int,
    val title: String,
    val userId: Int
)