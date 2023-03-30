package com.example.dummyproducts.data.user.repository

import com.example.dummyproducts.data.user.storage.UserStorage
import com.example.dummyproducts.data.user.storage.models.UserData
import com.example.dummyproducts.domain.user.models.User
import com.example.dummyproducts.domain.user.repository.UserRepository

class UserRepositoryImpl(private val userStorage: UserStorage): UserRepository {
    override suspend fun getUser(userName: String, password: String): User? {
        val userData = userStorage.getUser()
        return userData?.let { mapUserDataToDomain(userData = it) }
    }

    private fun mapUserDataToDomain(userData: UserData): User {
        return User(
            id = userData.id,
            username = userData.username,
            email = userData.email,
            firstName = userData.firstName,
            lastName = userData.lastName,
            gender = userData.gender,
            image = userData.image,
            token = userData.token
        )
    }
}