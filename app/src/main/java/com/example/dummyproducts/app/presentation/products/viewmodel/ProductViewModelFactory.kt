package com.example.dummyproducts.app.presentation.products.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.dummyproducts.data.database.AppDatabase
import com.example.dummyproducts.data.products.repository.ProductRepositoryImpl
import com.example.dummyproducts.data.products.retrofit.api.ProductApi
import com.example.dummyproducts.data.products.storage.ProductDbStorage
import com.example.dummyproducts.data.products.storage.ProductNetworkStorage
import com.example.dummyproducts.data.retrofit.AppRetrofit
import com.example.dummyproducts.domain.products.usecase.GetAllProducts
import com.example.dummyproducts.domain.products.usecase.GetUserProducts

class ProductViewModelFactory(context: Context): ViewModelProvider.Factory {
    // GetAllProducts
    private val retrofit by lazy { AppRetrofit.getRetrofit() }
    private val productApi by lazy { retrofit.create(ProductApi::class.java) }
    private val productNetworkStorage by lazy { ProductNetworkStorage(productApi = productApi) }
    private val productRepositoryImp by lazy { ProductRepositoryImpl(productStorage = productNetworkStorage) }
    private val getAllProducts by lazy { GetAllProducts(productRepository = productRepositoryImp) }
    // GetUserProducts
    private val db by lazy { AppDatabase.getDatabase(context = context) }
    private val productDao by lazy { db.productDao() }
    private val productDbStorage by lazy { ProductDbStorage(productDao = productDao) }
    private val productDbRepositoryImpl by lazy { ProductRepositoryImpl(productStorage = productDbStorage) }
    private val getUserProducts by lazy { GetUserProducts(productRepository = productDbRepositoryImpl) }

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ProductViewModel(
            getAllProducts = getAllProducts,
            getUserProducts = getUserProducts
        ) as T
    }
}