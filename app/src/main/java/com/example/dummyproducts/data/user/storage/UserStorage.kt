package com.example.dummyproducts.data.user.storage

import com.example.dummyproducts.data.user.storage.models.UserData

interface UserStorage {
    fun getUser(): UserData
}