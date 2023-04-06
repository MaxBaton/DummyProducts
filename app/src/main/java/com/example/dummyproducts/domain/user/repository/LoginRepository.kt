package com.example.dummyproducts.domain.user.repository

import com.example.dummyproducts.domain.user.models.User

interface LoginRepository {
    suspend fun login(
        userName: String,
        password: String
    ): User?
}