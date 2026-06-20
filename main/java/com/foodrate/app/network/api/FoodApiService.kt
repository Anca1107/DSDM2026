package com.foodrate.app.network.api

import com.foodrate.app.network.dto.MealResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface FoodApiService {

    @GET("filter.php")
    suspend fun getMealsByCategory(
        @Query("c") category: String
    ): MealResponseDto

    @GET("search.php")
    suspend fun searchMeals(
        @Query("s") searchText: String
    ): MealResponseDto
}
//def endpoint API