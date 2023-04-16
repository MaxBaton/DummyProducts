package com.example.dummyproducts.domain.products.models

data class ProductWithCheck(
    val product: Product,
    var isInUserList: Boolean
)
