package com.example.dummyproducts.domain.products.usecase

import com.example.dummyproducts.domain.products.repository.ProductRepository

class GetUserProducts(private val productRepository: ProductRepository) {
    suspend fun get() = productRepository.getProducts()
}