package com.example.dummyproducts.app.di.user

import com.example.dummyproducts.data.database.AppDatabase
import com.example.dummyproducts.data.database.user.UserDao
import com.example.dummyproducts.data.retrofit.AppRetrofit
import com.example.dummyproducts.data.user.repository.LoginRepositoryImpl
import com.example.dummyproducts.data.user.repository.UserRepositoryImpl
import com.example.dummyproducts.data.user.retrofit.api.UserApi
import com.example.dummyproducts.data.user.storage.UserDbStorage
import com.example.dummyproducts.data.user.storage.UserStorage
import com.example.dummyproducts.domain.user.repository.LoginRepository
import com.example.dummyproducts.domain.user.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataUserModule {
    @Provides
    @Singleton
    fun provideUserDao(appDatabase: AppDatabase): UserDao {
        return appDatabase.userDao()
    }

    @Provides
    @Singleton
    fun provideUserStorage(userDao: UserDao): UserStorage {
        return UserDbStorage(userDao = userDao)
    }

    @Provides
    @Singleton
    fun provideUserRepository(userStorage: UserStorage): UserRepository {
        return UserRepositoryImpl(userStorage = userStorage)
    }

    @Provides
    @Singleton
    fun provideUserApi(retrofit: Retrofit): UserApi {
        return retrofit.create(UserApi::class.java)
    }

    @Provides
    @Singleton
    fun provideLoginRepository(userApi: UserApi): LoginRepository {
        return LoginRepositoryImpl(userApi = userApi)
    }
}