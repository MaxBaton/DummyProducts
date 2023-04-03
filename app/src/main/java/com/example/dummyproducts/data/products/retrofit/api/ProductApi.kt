package com.example.dummyproducts.data.products.retrofit.api

import com.example.dummyproducts.data.products.storage.models.ProductsData
import retrofit2.http.GET

interface ProductApi {
    @GET("products")
    suspend fun getAllProducts(): ProductsData
}