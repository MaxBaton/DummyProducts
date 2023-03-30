package com.example.dummyproducts.app.presentation.user.viewModel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.dummyproducts.data.database.products.AppDatabase
import com.example.dummyproducts.data.retrofit.AppRetrofit
import com.example.dummyproducts.data.user.repository.UserRepositoryImpl
import com.example.dummyproducts.data.user.retrofit.api.UserApi
import com.example.dummyproducts.data.user.storage.UserDbStorage
import com.example.dummyproducts.data.user.storage.UserNetworkStorage
import com.example.dummyproducts.domain.user.usecase.GetUser
import com.example.dummyproducts.domain.user.usecase.LoginUser

class UserViewModelFactory(context: Context): ViewModelProvider.Factory {
    // GetUser
    private val userDao by lazy { AppDatabase.getDatabase(context = context).userDao() }
    private val userDbStorage by lazy { UserDbStorage(userDao = userDao) }
    private val userRepository by lazy { UserRepositoryImpl(userStorage = userDbStorage) }
    private val getUser by lazy { GetUser(userRepository = userRepository) }
    // LoginUser
    private val retrofit = AppRetrofit.getRetrofit()
    private val userApi by lazy { retrofit.create(UserApi::class.java) }
    private val userNetworkStorage by lazy { UserNetworkStorage(userApi = userApi) }
    private val userRepositoryNetwork by lazy { UserRepositoryImpl(userStorage = userNetworkStorage) }
    private val loginUser by lazy { LoginUser(userRepository =  userRepositoryNetwork) }

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return UserViewModel(
            getUser = getUser,
            loginUser = loginUser
        ) as T
    }
}