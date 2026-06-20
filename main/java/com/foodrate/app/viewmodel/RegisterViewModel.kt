package com.foodrate.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.foodrate.app.data.local.entity.UserEntity
import com.foodrate.app.data.repository.UserRepository
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val userRepository: UserRepository
) : ViewModel() {

    fun registerUser(
        name: String,
        email: String,
        password: String,
        onResult: (Boolean) -> Unit
    ) {
        viewModelScope.launch {

            val user = UserEntity(
                name = name,
                email = email,
                password = password
            )

            val success = userRepository.registerUser(user)

            onResult(success)
        }
    }
}