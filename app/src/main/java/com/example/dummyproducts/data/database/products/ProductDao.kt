package com.example.dummyproducts.data.database.products

import androidx.room.*
import com.example.dummyproducts.data.products.storage.models.ProductData

@Dao
interface ProductDao {
    @Query("select * from productData")
    fun getAllUserProducts(): List<ProductData>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addProduct(productData: ProductData)

    @Delete
    fun deleteProduct(productData: ProductData)
}