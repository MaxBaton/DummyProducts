package com.example.dummyproducts.domain.user.repository

import com.example.dummyproducts.domain.user.models.User

interface UserRepository {
    suspend fun getUser(
        userName: String = "",
        password: String = ""
    ): User?

    suspend fun saveUser(user: User): Boolean

    suspend fun deleteUser(user: User): Boolean
}