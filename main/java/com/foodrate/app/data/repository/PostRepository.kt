package com.foodrate.app.data.repository

import com.foodrate.app.network.RetrofitClient
import com.foodrate.app.network.dto.MealDto

class PostRepository {

    suspend fun getChickenMeals(): List<MealDto> {
        return RetrofitClient.api
            .getMealsByCategory("Chicken")
            .meals ?: emptyList()
    }

    suspend fun searchPizzaMeals(): List<MealDto> {
        return RetrofitClient.api
            .searchMeals("pizza")
            .meals ?: emptyList()
    }
}