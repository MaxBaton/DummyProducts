package com.example.dummyproducts.data.products.retrofit.api

import com.example.dummyproducts.data.products.storage.models.ProductsData
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers

interface ProductApi {
    @Headers("Content-Type': 'application/json")
    @GET("auth/products")
    suspend fun getAllProducts(@Header("Authorization") token: String): ProductsData
}