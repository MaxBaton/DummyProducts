package com.example.dummyproducts.data.products.storage

import com.example.dummyproducts.data.products.retrofit.api.ProductApi
import com.example.dummyproducts.data.products.storage.models.ProductData

class ProductNetworkStorage(private val productApi: ProductApi): ProductStorage {
    override suspend fun getAllProducts(): List<ProductData> {
        val products = productApi.getAllProducts().products
        return products
    }
}