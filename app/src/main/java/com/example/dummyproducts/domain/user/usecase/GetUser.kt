package com.example.dummyproducts.domain.user.usecase

import com.example.dummyproducts.domain.user.models.User
import com.example.dummyproducts.domain.user.repository.UserRepository

class GetUser(private val userRepository: UserRepository) {
    fun getLastUser(): User = userRepository.getUser()
}