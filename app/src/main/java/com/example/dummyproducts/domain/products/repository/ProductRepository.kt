package com.example.dummyproducts.domain.products.repository

import com.example.dummyproducts.domain.products.models.Product

interface ProductRepository {
    suspend fun getProducts(): List<Product>?

    suspend fun addProducts(products: List<Product>): Boolean
}