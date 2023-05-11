package com.example.dummyproducts.data.retrofit

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object AppRetrofit {
    private var INSTANCE: Retrofit? = null
    private const val BASE_URL = "https://dummyjson.com"
    private const val CONNECT_TIMEOUT: Long = 5
    private const val READ_WRITE_TIMEOUT: Long = 5

    fun getRetrofit(): Retrofit {
        if (INSTANCE == null) {
            synchronized(AppRetrofit::class.java) {
                if (INSTANCE == null) {
                    val interceptor = HttpLoggingInterceptor().apply {
                        level = HttpLoggingInterceptor.Level.BODY
                    }

                    val okHttpClient = OkHttpClient().newBuilder()
                        .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                        .readTimeout(READ_WRITE_TIMEOUT, TimeUnit.SECONDS)
                        .writeTimeout(READ_WRITE_TIMEOUT, TimeUnit.SECONDS)
                        .addInterceptor(interceptor)
                        .build()


                    INSTANCE = Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .client(okHttpClient)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()
                }
            }
        }

        return INSTANCE ?: throw Exception("Error creating Retrofit instance")
    }
}