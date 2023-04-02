package com.example.dummyproducts.domain.user.usecase

import com.example.dummyproducts.domain.user.models.User
import com.example.dummyproducts.domain.user.repository.UserRepository

class SaveUser(private val userRepository: UserRepository) {
    suspend fun save(user: User) = userRepository.saveUser(user = user)
}