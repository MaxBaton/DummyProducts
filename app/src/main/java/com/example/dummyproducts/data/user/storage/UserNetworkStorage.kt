package com.example.dummyproducts.data.user.storage

import com.example.dummyproducts.data.user.retrofit.api.UserApi
import com.example.dummyproducts.data.user.retrofit.models.AuthRequest
import com.example.dummyproducts.data.user.storage.models.UserData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserNetworkStorage(private val userApi: UserApi): UserStorage {
    override suspend fun getUser(userName: String, password: String): UserData? {
        try {
            val authRequest = AuthRequest(
                username = userName,
                password = password
            )
            val user = userApi.auth(authRequest)
            return user
        }catch (e: Exception) {
            return null
        }
    }

    override suspend fun saveUser(userData: UserData): Boolean {
        TODO("Not yet implemented")
    }
}