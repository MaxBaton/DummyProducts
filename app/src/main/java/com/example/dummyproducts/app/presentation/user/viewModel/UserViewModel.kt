package com.example.dummyproducts.app.presentation.user.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dummyproducts.domain.user.models.User
import com.example.dummyproducts.domain.user.usecase.GetUser
import com.example.dummyproducts.domain.user.usecase.LoginUser
import com.example.dummyproducts.domain.user.usecase.SaveUser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserViewModel(
    private val getUser: GetUser,
    private val loginUser: LoginUser,
    private val saveUser: SaveUser
): ViewModel() {
    private var mutableLiveDataUser = MutableLiveData<User>()
    val liveDataUser = mutableLiveDataUser

    fun getLastUser(onSuccessGet: () -> Unit, onErrorGet: () -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            val user = getUser.getLastUser()
            CoroutineScope(Dispatchers.Main).launch {
                user?.let { mutableLiveDataUser.value = it }
                if (user == null) {
                    onErrorGet()
                }else {
                    onSuccessGet()
                }
            }
        }
    }

    fun login(userName: String, password: String, onSuccessLogin: () -> Unit, onErrorLogin: () -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            val user = loginUser.login(
                userName = userName,
                password = password
            )
            CoroutineScope(Dispatchers.Main).launch {
                user?.let { mutableLiveDataUser.value = it }

                if (user != null) {
                    onSuccessLogin()
                } else {
                    onErrorLogin()
                }
            }
        }
    }

    fun saveUser(user: User?, onSuccessSave: () -> Unit, onErrorSave: () -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            val isSave = user?.let { saveUser.save(user = it) } ?: false
            CoroutineScope(Dispatchers.Main).launch {
                if (isSave) {
                    onSuccessSave()
                }else {
                    onErrorSave()
                }
            }
        }
    }
}