package com.example.dummyproducts.data.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AppRetrofit {
    companion object {
        private var INSTANCE: Retrofit? = null
        private const val BASE_URL = "https://dummyjson.com"

        fun getRetrofit(): Retrofit {
            if (INSTANCE == null) {
                synchronized(AppRetrofit::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = Retrofit.Builder()
                            .baseUrl(BASE_URL)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build()
                    }
                }
            }

            return INSTANCE!!
        }
    }
}