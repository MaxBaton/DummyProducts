package com.example.dummyproducts.data.user.storage.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserData(
    @PrimaryKey
    val id: Int,
    val username: String,
    val email: String,
    val firstName: String,
    val lastName: String,
    val gender: String,
    val image: String,
    val token: String
)
