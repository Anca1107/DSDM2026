package com.foodrate.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.foodrate.app.data.repository.PostRepository
import com.foodrate.app.network.dto.MealDto
import kotlinx.coroutines.launch

class PostViewModel(
    private val postRepository: PostRepository
) : ViewModel() {

    fun loadChickenMeals(
        onResult: (List<MealDto>) -> Unit
    ) {
        viewModelScope.launch {
            val meals = postRepository.getChickenMeals()
            onResult(meals)
        }
    }

    fun loadPizzaMeals(
        onResult: (List<MealDto>) -> Unit
    ) {
        viewModelScope.launch {
            val meals = postRepository.searchPizzaMeals()
            onResult(meals)
        }
    }
}