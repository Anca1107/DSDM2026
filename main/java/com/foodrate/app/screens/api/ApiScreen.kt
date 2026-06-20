package com.foodrate.app.screens.api

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.foodrate.app.network.dto.MealDto
import com.foodrate.app.viewmodel.PostViewModel

@Composable
fun ApiScreen(
    postViewModel: PostViewModel,
    onBackClick: () -> Unit
) {
    val chickenMeals = remember {
        mutableStateOf<List<MealDto>>(emptyList())
    }

    val pizzaMeals = remember {
        mutableStateOf<List<MealDto>>(emptyList())
    }

    LaunchedEffect(Unit) {
        postViewModel.loadChickenMeals { result ->
            chickenMeals.value = result.take(8)
        }

        postViewModel.loadPizzaMeals { result ->
            pizzaMeals.value = result.take(5)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(text = "Food data loaded from API")
        Text(text = "Chicken meals: ${chickenMeals.value.size}")
        Text(text = "Pizza search results: ${pizzaMeals.value.size}")

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .padding(top = 12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Text(text = "Chicken Meals")
            }

            items(chickenMeals.value) { meal ->
                MealCard(meal = meal)
            }

            item {
                Text(text = "Pizza Results")
            }

            items(pizzaMeals.value) { meal ->
                MealCard(meal = meal)
            }
        }

        Button(
            onClick = onBackClick,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp)
        ) {
            Text("Back")
        }
    }
}

@Composable
fun MealCard(
    meal: MealDto
) {
    Card(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = meal.strMeal)
            Text(text = "Meal ID: ${meal.idMeal}")
        }
    }
}