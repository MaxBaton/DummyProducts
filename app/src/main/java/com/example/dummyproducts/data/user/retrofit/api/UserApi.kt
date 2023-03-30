package com.example.dummyproducts.data.user.retrofit.api

import com.example.dummyproducts.data.user.retrofit.models.AuthRequest
import com.example.dummyproducts.data.user.storage.models.UserData
import retrofit2.http.Body
import retrofit2.http.POST

interface UserApi {

    @POST("auth/login")
    suspend fun auth(@Body authRequest: AuthRequest): UserData
}