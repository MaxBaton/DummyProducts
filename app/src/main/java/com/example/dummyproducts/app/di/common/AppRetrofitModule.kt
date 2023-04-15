package com.example.dummyproducts.app.di.common

import com.example.dummyproducts.data.retrofit.AppRetrofit
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppRetrofitModule {
    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return AppRetrofit.getRetrofit()
    }
}