package com.example.dummyproducts.data.common

import com.example.dummyproducts.data.products.storage.models.ProductData
import com.example.dummyproducts.data.products.storage.models.ProductsData
import com.example.dummyproducts.data.user.storage.models.UserData
import com.example.dummyproducts.domain.products.models.Product
import com.example.dummyproducts.domain.products.models.Products
import com.example.dummyproducts.domain.user.models.User

fun User.mapToUserData(): UserData {
    return UserData(
        id = id,
        username = username,
        email = email,
        firstName = firstName,
        lastName = lastName,
        gender = gender,
        image = image,
        token = token
    )
}

fun UserData.mapToUserDomain(): User {
    return User(
        id = id,
        username = username,
        email = email,
        firstName = firstName,
        lastName = lastName,
        gender = gender,
        image = image,
        token = token
    )
}

fun Product.mapToProductData(): ProductData {
    return ProductData(
        brand = brand,
        category = category,
        description = description,
        discountPercentage = discountPercentage,
        id = id,
        images = images,
        price = price,
        rating = rating,
        stock = stock,
        thumbnail = thumbnail,
        title = title
    )
}

fun ProductData.mapToProductDomain(): Product {
    return Product(
        brand = brand,
        category = category,
        description = description,
        discountPercentage = discountPercentage,
        id = id,
        images = images,
        price = price,
        rating = rating,
        stock = stock,
        thumbnail = thumbnail,
        title = title
    )
}

fun Products.mapToProductsData(): ProductsData {
    val list = mutableListOf<ProductData>()
    this.products.forEach {
        val productData = it.mapToProductData()
        list.add(productData)
    }
    return ProductsData(products = list)
}

fun ProductsData.mapToProductsDomain(): Products {
    val list = mutableListOf<Product>()
    this.products.forEach {
        val productData = it.mapToProductDomain()
        list.add(productData)
    }
    return Products(products = list)
}

fun List<ProductData>.mapToListProductDomain(): List<Product> {
    val listDomain = mutableListOf<Product>()
    this.forEach {
        val productDomain = it.mapToProductDomain()
        listDomain.add(productDomain)
    }
    return listDomain
}