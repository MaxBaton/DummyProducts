package com.example.dummyproducts.app.di.products

import com.example.dummyproducts.data.database.AppDatabase
import com.example.dummyproducts.data.database.products.ProductDao
import com.example.dummyproducts.data.products.repository.ProductRepositoryImpl
import com.example.dummyproducts.data.products.retrofit.api.ProductApi
import com.example.dummyproducts.data.products.storage.ProductDbStorage
import com.example.dummyproducts.data.products.storage.ProductNetworkStorage
import com.example.dummyproducts.data.products.storage.ProductStorage
import com.example.dummyproducts.domain.products.repository.ProductRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataProductModule {
    @Provides
    @Singleton
    fun provideProductDao(appDatabase: AppDatabase): ProductDao {
        return appDatabase.productDao()
    }

    @Provides
    @Singleton
    fun provideProductApi(retrofit: Retrofit): ProductApi {
        return retrofit.create(ProductApi::class.java)
    }

    @Provides
    @Singleton
    @com.example.dummyproducts.app.di.products.ProductDbStorage
    fun provideProductDbStorage(productDao: ProductDao): ProductStorage {
        return ProductDbStorage(productDao = productDao)
    }

    @Provides
    @Singleton
    @com.example.dummyproducts.app.di.products.ProductNetworkStorage
    fun provideProductNetworkStorage(productApi: ProductApi): ProductStorage {
        return ProductNetworkStorage(productApi = productApi)
    }

    @Provides
    @Singleton
    @ProductNetworkRepository
    fun provideProductRepository(
        @com.example.dummyproducts.app.di.products.ProductNetworkStorage
        productStorage: ProductStorage
    ): ProductRepository {
        return ProductRepositoryImpl(productStorage = productStorage)
    }

    @Provides
    @Singleton
    @ProductDbRepository
    fun provideProductDBRepository(
        @com.example.dummyproducts.app.di.products.ProductDbStorage
        productStorage: ProductStorage
    ): ProductRepository {
        return ProductRepositoryImpl( productStorage = productStorage)
    }
}