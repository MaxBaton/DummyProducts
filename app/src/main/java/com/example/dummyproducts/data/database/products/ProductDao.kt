package com.example.dummyproducts.data.database.products

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.dummyproducts.data.products.storage.models.ProductData

@Dao
interface ProductDao {
    @Query("select * from productData")
    fun getAllUserProducts(): List<ProductData>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addProduct(productData: ProductData)
}