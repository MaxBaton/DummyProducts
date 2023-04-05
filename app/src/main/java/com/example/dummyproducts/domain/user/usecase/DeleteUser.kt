package com.example.dummyproducts.domain.user.usecase

import com.example.dummyproducts.domain.user.models.User
import com.example.dummyproducts.domain.user.repository.UserRepository

class DeleteUser(private val userRepository: UserRepository) {
    suspend fun delete(user: User) = userRepository.deleteUser(user = user)
}