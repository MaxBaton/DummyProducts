package com.example.dummyproducts.app.presentation.user.viewModel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.dummyproducts.data.database.products.AppDatabase
import com.example.dummyproducts.data.user.repository.UserRepositoryImpl
import com.example.dummyproducts.data.user.storage.UserDbStorage
import com.example.dummyproducts.domain.user.usecase.GetUser

class UserViewModelFactory(context: Context): ViewModelProvider.Factory {
    private val userDao by lazy { AppDatabase.getDatabase(context = context).userDao() }
    private val userDbStorage by lazy { UserDbStorage(userDao = userDao) }
    private val userRepository by lazy { UserRepositoryImpl(userStorage = userDbStorage) }
    private val getUser by lazy { GetUser(userRepository = userRepository) }

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return UserViewModel(
            getUser = getUser
        ) as T
    }
}