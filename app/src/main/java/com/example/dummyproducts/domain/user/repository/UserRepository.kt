package com.example.dummyproducts.domain.user.repository

import com.example.dummyproducts.domain.user.models.User

interface UserRepository {
    fun getUser(): User
}