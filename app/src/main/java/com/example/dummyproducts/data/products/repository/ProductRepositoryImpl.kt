package com.example.dummyproducts.data.products.repository

import com.example.dummyproducts.data.common.mapToListProductData
import com.example.dummyproducts.data.common.mapToListProductDomain
import com.example.dummyproducts.data.common.mapToProductDomain
import com.example.dummyproducts.data.products.storage.ProductStorage
import com.example.dummyproducts.domain.products.models.Product
import com.example.dummyproducts.domain.products.repository.ProductRepository

class ProductRepositoryImpl(private val productStorage: ProductStorage): ProductRepository {
    override suspend fun getProducts(token: String): List<Product> {
        val productsData = productStorage.getProducts(token = token)
        val productsDomain = productsData?.mapToListProductDomain()

        return productsDomain ?: emptyList()
    }

    override suspend fun addProducts(products: List<Product>): Boolean {
        return try {
            val productsData = products.mapToListProductData()
            productStorage.addProducts(productsData = productsData)
            true
        }catch (e: Exception) {
            false
        }
    }

    override suspend fun deleteProducts(products: List<Product>): Boolean {
        return try {
            val productsData = products.mapToListProductData()
            productStorage.deleteProducts(productsData = productsData)
            true
        }catch (e: Exception) {
            false
        }
    }
}