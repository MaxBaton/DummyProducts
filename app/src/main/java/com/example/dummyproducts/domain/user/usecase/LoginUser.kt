package com.example.dummyproducts.domain.user.usecase

import com.example.dummyproducts.domain.user.models.User
import com.example.dummyproducts.domain.user.repository.UserRepository

class LoginUser(private val userRepository: UserRepository) {
    suspend fun login(userName: String, password: String): User? = userRepository.getUser(userName, password)
}