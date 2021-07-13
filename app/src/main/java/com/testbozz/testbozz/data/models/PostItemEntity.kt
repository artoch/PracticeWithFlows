package com.testbozz.testbozz.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "post_table")
data class PostItemEntity(
    @PrimaryKey(autoGenerate = false)
    val id:Long,
    val userId: Int,
    val title: String,
    val body: String,
){
    class PostItemResponse:ArrayList<PostItemEntity>()
}