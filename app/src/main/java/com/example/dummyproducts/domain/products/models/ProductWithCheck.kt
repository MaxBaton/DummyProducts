package com.example.dummyproducts.domain.products.models

data class ProductWithCheck(
    val product: Product,
    val isInUserList: Boolean
)
