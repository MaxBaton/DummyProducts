package com.example.dummyproducts.app.presentation.user.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dummyproducts.domain.user.models.User
import com.example.dummyproducts.domain.user.usecase.GetUser
import com.example.dummyproducts.domain.user.usecase.LoginUser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserViewModel(
    private val getUser: GetUser,
    private val loginUser: LoginUser
): ViewModel() {
    private var mutableLiveDataUser = MutableLiveData<User>()
    val liveDataUser = mutableLiveDataUser

    fun getLastUser() {
        CoroutineScope(Dispatchers.IO).launch {
            val user = getUser.getLastUser()
            user?.let { mutableLiveDataUser.value = it }
        }
    }

    fun login(userName: String, password: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val user = loginUser.login(
                userName = userName,
                password = password
            )
            user?.let { mutableLiveDataUser.value = it }
        }
    }
}