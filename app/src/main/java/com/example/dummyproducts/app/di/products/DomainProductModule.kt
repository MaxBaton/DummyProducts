package com.example.dummyproducts.app.di.products

import com.example.dummyproducts.domain.products.repository.ProductRepository
import com.example.dummyproducts.domain.products.usecase.AddProducts
import com.example.dummyproducts.domain.products.usecase.GetAllProducts
import com.example.dummyproducts.domain.products.usecase.GetUserProducts
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class DomainProductModule {
    @Provides
    fun provideGetAllProducts(
        @ProductNetworkRepository
        productRepository: ProductRepository
    ): GetAllProducts {
        return GetAllProducts(productRepository = productRepository)
    }

    @Provides
    fun provideGetUserProducts(
        @ProductDbRepository
        productRepository: ProductRepository
    ): GetUserProducts {
        return GetUserProducts(productRepository = productRepository)
    }

    @Provides
    fun provideAddProducts(
        @ProductDbRepository
        productRepository: ProductRepository
    ): AddProducts {
        return AddProducts(productRepository = productRepository)
    }
}