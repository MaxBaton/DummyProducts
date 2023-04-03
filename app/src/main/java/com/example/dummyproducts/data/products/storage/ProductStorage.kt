package com.example.dummyproducts.data.products.storage

import com.example.dummyproducts.data.products.storage.models.ProductData

interface ProductStorage {
    suspend fun getAllProducts(): List<ProductData>
}