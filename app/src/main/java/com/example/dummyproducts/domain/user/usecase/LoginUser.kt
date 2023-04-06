package com.example.dummyproducts.domain.user.usecase

import com.example.dummyproducts.domain.user.models.User
import com.example.dummyproducts.domain.user.repository.LoginRepository
import com.example.dummyproducts.domain.user.repository.UserRepository

class LoginUser(private val loginRepository: LoginRepository) {
    suspend fun login(userName: String, password: String): User? = loginRepository.login(
        userName = userName,
        password = password
    )
}