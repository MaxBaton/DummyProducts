package com.example.dummyproducts.data.user.storage

import com.example.dummyproducts.data.user.storage.models.UserData

interface UserStorage {
    suspend fun getUser(
        userName: String = "",
        password: String = ""
    ): UserData?

    suspend fun saveUser(userData: UserData): Boolean
}