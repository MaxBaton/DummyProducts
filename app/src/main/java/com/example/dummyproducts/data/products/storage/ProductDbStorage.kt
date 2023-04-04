package com.example.dummyproducts.data.products.storage

import com.example.dummyproducts.data.database.products.ProductDao
import com.example.dummyproducts.data.products.storage.ProductStorage
import com.example.dummyproducts.data.products.storage.models.ProductData

class ProductDbStorage(private val productDao: ProductDao): ProductStorage {
    override suspend fun getProducts(): List<ProductData>? {
        return productDao.getAllUserProducts()
    }

}