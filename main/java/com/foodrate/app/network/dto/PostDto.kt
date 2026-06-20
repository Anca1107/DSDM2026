package com.foodrate.app.network.dto

data class PostDto(
    val userId: Int,
    val id: Int,
    val title: String,
    val body: String
)