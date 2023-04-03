package com.example.dummyproducts.app.presentation.products.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.dummyproducts.data.products.repository.ProductRepositoryImpl
import com.example.dummyproducts.data.products.retrofit.api.ProductApi
import com.example.dummyproducts.data.products.storage.ProductNetworkStorage
import com.example.dummyproducts.data.retrofit.AppRetrofit
import com.example.dummyproducts.domain.products.usecase.GetProducts

class ProductViewModelFactory: ViewModelProvider.Factory {
    private val retrofit by lazy { AppRetrofit.getRetrofit() }
    private val productApi by lazy { retrofit.create(ProductApi::class.java) }
    private val productNetworkStorage by lazy { ProductNetworkStorage(productApi = productApi) }
    private val productRepositoryImp by lazy { ProductRepositoryImpl(productStorage = productNetworkStorage) }
    private val getProducts by lazy { GetProducts(productRepository = productRepositoryImp) }

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ProductViewModel(
            getProducts = getProducts
        ) as T
    }
}