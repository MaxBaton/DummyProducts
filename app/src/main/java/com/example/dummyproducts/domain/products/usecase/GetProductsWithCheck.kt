package com.example.dummyproducts.domain.products.usecase

import com.example.dummyproducts.domain.products.models.Product
import com.example.dummyproducts.domain.products.models.ProductWithCheck
import okhttp3.internal.toImmutableList

class GetProductsWithCheck(
    private val allProducts: List<Product>,
    private val userProducts: List<Product>
) {
    fun get(): List<ProductWithCheck> {
        val list = mutableListOf<ProductWithCheck>()
        allProducts.forEach {
            val isInUserList = userProducts.contains(it)
            val product = it
            val productWithCheck = ProductWithCheck(
                product = product,
                isInUserList = isInUserList
            )
            list.add(productWithCheck)
        }
        return list.toImmutableList()
    }
}