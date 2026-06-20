package com.foodrate.app.data.repository

import com.foodrate.app.data.local.dao.UserDao
import com.foodrate.app.data.local.entity.UserEntity

class UserRepository(
    private val userDao: UserDao
) {
    suspend fun registerUser(user: UserEntity): Boolean {
        val existingUser = userDao.getUserByEmail(user.email)

        return if (existingUser == null) {
            userDao.insertUser(user)
            true
        } else {
            false
        }
    }

    suspend fun loginUser(email: String, password: String): Boolean {
        val user = userDao.loginUser(email, password)
        return user != null
    }
    suspend fun getAllUsers(): List<UserEntity> {
        return userDao.getAllUsers()
    }
}