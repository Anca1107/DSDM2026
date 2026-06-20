package com.foodrate.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.foodrate.app.data.local.entity.UserEntity
import com.foodrate.app.data.repository.UserRepository
import kotlinx.coroutines.launch

class UsersViewModel(
    private val userRepository: UserRepository
) : ViewModel() {

    fun loadUsers(
        onResult: (List<UserEntity>) -> Unit
    ) {
        viewModelScope.launch {
            val users = userRepository.getAllUsers()
            onResult(users)
        }
    }
}