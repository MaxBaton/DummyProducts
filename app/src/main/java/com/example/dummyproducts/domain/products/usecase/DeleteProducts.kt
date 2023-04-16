package com.example.dummyproducts.domain.products.usecase

import com.example.dummyproducts.domain.products.models.Product
import com.example.dummyproducts.domain.products.repository.ProductRepository

class DeleteProducts(private val productRepository: ProductRepository) {
    suspend fun deleteProducts(products: List<Product>) = productRepository.deleteProducts(products = products)
}