package com.example.dummyproducts.domain.products.usecase

import com.example.dummyproducts.domain.products.models.Product
import com.example.dummyproducts.domain.products.repository.ProductRepository

class GetProducts(private val productRepository: ProductRepository) {
    suspend fun getAllProducts(): List<Product>? = productRepository.getAllProducts()
}