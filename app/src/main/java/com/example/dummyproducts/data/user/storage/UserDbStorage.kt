package com.example.dummyproducts.data.user.storage

import com.example.dummyproducts.data.database.user.UserDao
import com.example.dummyproducts.data.user.storage.models.UserData

class UserDbStorage(private val userDao: UserDao): UserStorage {
    override suspend fun getUser(userName: String, password: String): UserData? {
        return userDao.getLastUser()
    }
}