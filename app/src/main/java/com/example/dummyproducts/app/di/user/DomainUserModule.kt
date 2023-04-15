package com.example.dummyproducts.app.di.user

import com.example.dummyproducts.domain.user.repository.LoginRepository
import com.example.dummyproducts.domain.user.repository.UserRepository
import com.example.dummyproducts.domain.user.usecase.DeleteUser
import com.example.dummyproducts.domain.user.usecase.GetUser
import com.example.dummyproducts.domain.user.usecase.LoginUser
import com.example.dummyproducts.domain.user.usecase.SaveUser
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class DomainUserModule {
    @Provides
    fun provideGetUser(userRepository: UserRepository): GetUser {
        return GetUser(userRepository = userRepository)
    }

    @Provides
    fun provideSaveUser(userRepository: UserRepository): SaveUser {
        return SaveUser(userRepository = userRepository)
    }

    @Provides
    fun provideDeleteUser(userRepository: UserRepository): DeleteUser {
        return DeleteUser(userRepository = userRepository)
    }

    @Provides
    fun provideLoginUser(loginRepository: LoginRepository): LoginUser {
        return LoginUser(loginRepository = loginRepository)
    }
}