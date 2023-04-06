package com.example.dummyproducts.app.presentation.user.viewModel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.dummyproducts.data.database.AppDatabase
import com.example.dummyproducts.data.retrofit.AppRetrofit
import com.example.dummyproducts.data.user.repository.LoginRepositoryImpl
import com.example.dummyproducts.data.user.repository.UserRepositoryImpl
import com.example.dummyproducts.data.user.retrofit.api.UserApi
import com.example.dummyproducts.data.user.storage.UserDbStorage
import com.example.dummyproducts.domain.user.usecase.DeleteUser
import com.example.dummyproducts.domain.user.usecase.GetUser
import com.example.dummyproducts.domain.user.usecase.LoginUser
import com.example.dummyproducts.domain.user.usecase.SaveUser

class UserViewModelFactory(context: Context): ViewModelProvider.Factory {
    // GetUser
    private val userDao by lazy { AppDatabase.getDatabase(context = context).userDao() }
    private val userDbStorage by lazy { UserDbStorage(userDao = userDao) }
    private val userRepositoryDB by lazy { UserRepositoryImpl(userStorage = userDbStorage) }
    private val getUser by lazy { GetUser(userRepository = userRepositoryDB) }
    // LoginUser
    private val retrofit = AppRetrofit.getRetrofit()
    private val userApi by lazy { retrofit.create(UserApi::class.java) }
    private val loginRepositoryImpl by lazy { LoginRepositoryImpl(userApi = userApi) }
    private val loginUser by lazy { LoginUser(loginRepository = loginRepositoryImpl) }
    // SaveUser
    private val saveUser by lazy { SaveUser(userRepository = userRepositoryDB) }
    // DeleteUser
    private val deleteUser by lazy { DeleteUser(userRepository = userRepositoryDB) }

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return UserViewModel(
            getUser = getUser,
            loginUser = loginUser,
            saveUser = saveUser,
            deleteUser = deleteUser
        ) as T
    }
}