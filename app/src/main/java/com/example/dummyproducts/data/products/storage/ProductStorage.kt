package com.example.dummyproducts.data.products.storage

import com.example.dummyproducts.data.products.storage.models.ProductData

interface ProductStorage {
    suspend fun getProducts(): List<ProductData>?

    suspend fun addProducts(productsData: List<ProductData>): Boolean
}