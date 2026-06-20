package com.foodrate.app.screens.details

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.foodrate.app.data.Restaurant

@Composable
fun RestaurantDetailsScreen(
    restaurant: Restaurant,
    onBackClick: () -> Unit
) {
    val reviewText = remember { mutableStateOf("") }
    val ratingText = remember { mutableStateOf("") }
//salvare locala
    val reviews = remember {
        mutableStateListOf<Pair<String, Int>>()
    }

    val averageRating = if (reviews.isNotEmpty()) {
        reviews.map { it.second }.average()
    } else {
        restaurant.rating
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        Image(
            painter = painterResource(id = restaurant.imageRes),
            contentDescription = restaurant.name,
            modifier = Modifier
                .fillMaxWidth()
                .height(160.dp),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(text = restaurant.name)
        Spacer(modifier = Modifier.height(8.dp))

        Text(text = "Cuisine: ${restaurant.cuisine}")
        Text(text = "Initial rating: ${restaurant.rating}")
        Text(text = "Current average rating: %.1f".format(averageRating))

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = reviewText.value,
            onValueChange = { reviewText.value = it },
            label = { Text("Write a review") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = ratingText.value,
            onValueChange = { ratingText.value = it },
            label = { Text("Rating 1-5") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                val rating = ratingText.value.toIntOrNull()

                if (
                    reviewText.value.isNotBlank() &&
                    rating != null &&
                    rating in 1..5
                ) {
                    reviews.add(reviewText.value to rating)
                    reviewText.value = ""
                    ratingText.value = ""
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Add Review")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Reviews")

        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(reviews) { review ->
                Card(
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 4.dp
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(12.dp)
                    ) {
                        Text(text = "Rating: ${review.second}/5")
                        Text(text = review.first)
                    }
                }
            }
        }

        Button(
            onClick = onBackClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Back")
        }
    }
}