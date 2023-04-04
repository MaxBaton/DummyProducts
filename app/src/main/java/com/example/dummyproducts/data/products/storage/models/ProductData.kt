package com.example.dummyproducts.data.products.storage.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.dummyproducts.data.database.products.convertors.ProductImagesConvertor

@Entity
@TypeConverters(ProductImagesConvertor::class)
data class ProductData(
    val brand: String,
    val category: String,
    val description: String,
    val discountPercentage: Double,
    @PrimaryKey
    val id: Int,
    val images: List<String>,
    val price: Int,
    val rating: Double,
    val stock: Int,
    val thumbnail: String,
    val title: String
)
