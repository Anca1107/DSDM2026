package com.foodrate.app.data

data class Restaurant(
    val id: Int,
    val name: String,
    val cuisine: String,
    val rating: Float,
    val imageRes: Int
)