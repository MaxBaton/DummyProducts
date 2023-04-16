package com.example.dummyproducts.data.products.storage

import com.example.dummyproducts.data.products.retrofit.api.ProductApi
import com.example.dummyproducts.data.products.storage.models.ProductData

class ProductNetworkStorage(private val productApi: ProductApi): ProductStorage {
    override suspend fun getProducts(): List<ProductData>? {
        return try {
            val products = productApi.getAllProducts().products
            products
        }catch (e: Exception) {
            null
        }
    }

    override suspend fun addProducts(productsData: List<ProductData>): Boolean {
        TODO("Not yet implemented")
    }
}