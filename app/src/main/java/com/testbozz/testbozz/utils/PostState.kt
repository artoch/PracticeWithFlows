package com.testbozz.testbozz.utils

import com.testbozz.testbozz.data.models.PostItemEntity

sealed class PostState {
    data class Success(val postItemEntity: PostItemEntity.PostItemResponse):   PostState()
    object Error:   PostState()
}