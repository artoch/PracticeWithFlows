package com.testbozz.testbozz.data.models

data class CommentsPostEntityItem(
    val body: String,
    val email: String,
    val id: Int,
    val name: String,
    val postId: Int
){
    class CommentsPostEntity: ArrayList<CommentsPostEntityItem>()
}