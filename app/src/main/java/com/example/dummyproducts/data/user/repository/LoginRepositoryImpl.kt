package com.example.dummyproducts.data.user.repository

import com.example.dummyproducts.data.common.mapToUserDomain
import com.example.dummyproducts.data.user.retrofit.api.UserApi
import com.example.dummyproducts.data.user.retrofit.models.AuthRequest
import com.example.dummyproducts.domain.user.models.User
import com.example.dummyproducts.domain.user.repository.LoginRepository

class LoginRepositoryImpl(private val userApi: UserApi): LoginRepository {
    override suspend fun login(userName: String, password: String): User? {
        return try {
            val authRequest = AuthRequest(
                username = userName,
                password = password
            )
            val userData = userApi.auth(authRequest)
            userData.mapToUserDomain()
        }catch (e: Exception) {
            null
        }
    }

}