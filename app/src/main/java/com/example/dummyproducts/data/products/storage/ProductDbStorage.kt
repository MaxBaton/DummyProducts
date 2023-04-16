package com.example.dummyproducts.data.products.storage

import com.example.dummyproducts.data.database.products.ProductDao
import com.example.dummyproducts.data.products.storage.ProductStorage
import com.example.dummyproducts.data.products.storage.models.ProductData

class ProductDbStorage(private val productDao: ProductDao): ProductStorage {
    override suspend fun getProducts(): List<ProductData>? {
        return productDao.getAllUserProducts()
    }

    override suspend fun addProducts(productsData: List<ProductData>): Boolean {
        return try {
            productsData.forEach {
                productDao.addProduct(productData = it)
            }
            true
        }catch (e: Exception) {
            false
        }
    }

}