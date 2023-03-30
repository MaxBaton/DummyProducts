package com.example.dummyproducts.app.presentation.user.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dummyproducts.domain.user.models.User
import com.example.dummyproducts.domain.user.usecase.GetUser

class UserViewModel(
    private val getUser: GetUser
): ViewModel() {
    private var mutableLiveDataUser = MutableLiveData<User>()
    val liveDataUser = mutableLiveDataUser

    fun getLastUser() {
        val user = getUser.getLastUser()
        mutableLiveDataUser.value = user
    }
}