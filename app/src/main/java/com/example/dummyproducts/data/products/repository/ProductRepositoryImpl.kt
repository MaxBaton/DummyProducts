package com.example.dummyproducts.data.products.repository

import com.example.dummyproducts.data.common.mapToProductDomain
import com.example.dummyproducts.data.common.mapToProductsDomain
import com.example.dummyproducts.data.products.storage.ProductStorage
import com.example.dummyproducts.domain.products.models.Product
import com.example.dummyproducts.domain.products.repository.ProductRepository

class ProductRepositoryImpl(private val productStorage: ProductStorage): ProductRepository {
    override suspend fun getAllProducts(): List<Product> {
        val productsData = productStorage.getAllProducts()
        val productsDomain = mutableListOf<Product>()
        productsData.forEach {
            val productDomain = it.mapToProductDomain()
            productsDomain.add(productDomain)
        }

        return productsDomain
    }
}