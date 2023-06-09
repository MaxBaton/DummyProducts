package com.example.dummyproducts.data.user.storage

import com.example.dummyproducts.data.database.user.UserDao
import com.example.dummyproducts.data.user.storage.models.UserData

class UserDbStorage(private val userDao: UserDao): UserStorage {
    override suspend fun getUser(): UserData? {
        return userDao.getLastUser()
    }

    override suspend fun saveUser(userData: UserData): Boolean {
        return try {
            userDao.saveUser(userData = userData)
            true
        }catch (e: Exception) {
            false
        }
    }

    override suspend fun deleteUser(userData: UserData): Boolean {
        return try {
            userDao.deleteUser(userData = userData)
            true
        }catch (e: Exception) {
            false
        }
    }
}