package com.example.dummyproducts.data.user.repository

import com.example.dummyproducts.data.common.mapToUserData
import com.example.dummyproducts.data.common.mapToUserDomain
import com.example.dummyproducts.data.user.storage.UserStorage
import com.example.dummyproducts.data.user.storage.models.UserData
import com.example.dummyproducts.domain.user.models.User
import com.example.dummyproducts.domain.user.repository.UserRepository

class UserRepositoryImpl(private val userStorage: UserStorage): UserRepository {
    override suspend fun getUser(userName: String, password: String): User? {
        val userData = userStorage.getUser(userName = userName, password = password)
        return userData?.mapToUserDomain()
    }

    override suspend fun saveUser(user: User): Boolean {
        return userStorage.saveUser(userData = user.mapToUserData())
    }
}