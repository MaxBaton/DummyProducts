package com.example.dummyproducts.data.user.storage

import com.example.dummyproducts.data.user.storage.models.UserData

interface UserStorage {
    suspend fun getUser(): UserData?

    suspend fun saveUser(userData: UserData): Boolean

    suspend fun deleteUser(userData: UserData): Boolean
}