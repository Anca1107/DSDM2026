package com.foodrate.app

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.*
import androidx.lifecycle.lifecycleScope
import com.foodrate.app.data.Restaurant
import com.foodrate.app.data.local.database.FoodRateDatabase
import com.foodrate.app.data.repository.PostRepository
import com.foodrate.app.data.repository.UserRepository
import com.foodrate.app.datastore.UserPreferences
import com.foodrate.app.screens.api.ApiScreen
import com.foodrate.app.screens.auth.LoginScreen
import com.foodrate.app.screens.auth.RegisterScreen
import com.foodrate.app.screens.details.RestaurantDetailsScreen
import com.foodrate.app.screens.home.HomeScreen
import com.foodrate.app.ui.theme.FoodRateTheme
import com.foodrate.app.viewmodel.LoginViewModel
import com.foodrate.app.viewmodel.PostViewModel
import com.foodrate.app.viewmodel.RegisterViewModel
import kotlinx.coroutines.launch
import com.foodrate.app.screens.users.UsersScreen
import com.foodrate.app.viewmodel.UsersViewModel
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val database = FoodRateDatabase.getDatabase(applicationContext)
        val userRepository = UserRepository(database.userDao())
        val loginViewModel = LoginViewModel(userRepository)
        val registerViewModel = RegisterViewModel(userRepository)
        val usersViewModel = UsersViewModel(userRepository)
        val userPreferences = UserPreferences(applicationContext)

        val postRepository = PostRepository()
        val postViewModel = PostViewModel(postRepository)

        setContent {
            FoodRateTheme {
                //nav
                var currentScreen by remember { mutableStateOf("login") }
                var selectedRestaurant by remember { mutableStateOf<Restaurant?>(null) }

                when (currentScreen) {
                    "login" -> LoginScreen(
                        onLoginClick = { email, password ->
                            loginViewModel.loginUser(email, password) { success ->
                                if (success) {
                                    lifecycleScope.launch {
                                        userPreferences.saveUserEmail(email)
                                    }

                                    Toast.makeText(
                                        this,
                                        "Login successful",
                                        Toast.LENGTH_SHORT
                                    ).show()

                                    currentScreen = "home"
                                } else {
                                    Toast.makeText(
                                        this,
                                        "Invalid email or password",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        },
                        onRegisterClick = {
                            currentScreen = "register"
                        }
                    )

                    "register" -> RegisterScreen(
                        onRegisterClick = { name, email, password ->
                            registerViewModel.registerUser(name, email, password) { success ->
                                if (success) {
                                    Toast.makeText(
                                        this,
                                        "Account created successfully",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    currentScreen = "login"
                                } else {
                                    Toast.makeText(
                                        this,
                                        "Email already exists",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        },
                        onBackToLoginClick = {
                            currentScreen = "login"
                        }
                    )

                    "home" -> HomeScreen(
                        onRestaurantClick = { restaurant ->
                            selectedRestaurant = restaurant
                            currentScreen = "details"
                        },
                        onApiClick = {
                            currentScreen = "api"
                        },
                        onLogoutClick = {
                            lifecycleScope.launch {
                                userPreferences.clearUserEmail()
                            }

                            currentScreen = "login"
                        },
                        onUsersClick = {
                            currentScreen = "users"
                        },

                    )

                    "details" -> {
                        selectedRestaurant?.let { restaurant ->
                            RestaurantDetailsScreen(
                                restaurant = restaurant,
                                onBackClick = {
                                    currentScreen = "home"
                                }
                            )
                        }
                    }

                    "api" -> ApiScreen(
                        postViewModel = postViewModel,
                        onBackClick = {
                            currentScreen = "home"
                        }
                    )
                    "users" -> UsersScreen(
                        usersViewModel = usersViewModel,
                        onBackClick = {
                            currentScreen = "home"
                        }
                    )
                }
            }
        }
    }
}