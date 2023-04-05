package com.example.dummyproducts.domain.products.usecase

class GetPriceStr(private val price: Int) {
    fun get() = "$price $"
}