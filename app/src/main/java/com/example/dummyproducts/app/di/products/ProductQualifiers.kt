package com.example.dummyproducts.app.di.products

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class ProductDbStorage


@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class ProductNetworkStorage

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class ProductDbRepository


@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class ProductNetworkRepository