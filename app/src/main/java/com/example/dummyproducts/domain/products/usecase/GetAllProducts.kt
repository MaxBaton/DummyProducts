package com.example.dummyproducts.domain.products.usecase

import com.example.dummyproducts.domain.products.models.Product
import com.example.dummyproducts.domain.products.repository.ProductRepository

class GetAllProducts(private val productRepository: ProductRepository) {
    suspend fun get(): List<Product>? = productRepository.getProducts()
}