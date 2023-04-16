package com.example.dummyproducts.domain.products.usecase

import com.example.dummyproducts.domain.products.models.Product
import com.example.dummyproducts.domain.products.repository.ProductRepository

class AddProducts(private val productRepository: ProductRepository) {
    suspend fun add(products: List<Product>) = productRepository.addProducts(products = products)
}