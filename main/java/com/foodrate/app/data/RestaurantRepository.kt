package com.foodrate.app.data

import com.foodrate.app.R

object RestaurantRepository {

    val restaurants = listOf(
        Restaurant(1, "McDonald's", "Fast Food", 4.5f, R.drawable.mcdonalds),
        Restaurant(2, "KFC", "Chicken", 4.2f, R.drawable.kfc),
        Restaurant(3, "Pizza Hut", "Pizza", 4.6f, R.drawable.pizza_hut),
        Restaurant(4, "Spartan", "Greek Food", 4.4f, R.drawable.spartan),
        Restaurant(5, "Mesopotamia", "Turkish Food", 4.7f, R.drawable.mesopotamia)
    )
}