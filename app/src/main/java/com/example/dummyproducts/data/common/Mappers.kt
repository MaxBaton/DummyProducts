package com.example.dummyproducts.data.common

import com.example.dummyproducts.data.user.storage.models.UserData
import com.example.dummyproducts.domain.user.models.User

fun User.mapToUserData(): UserData {
    return UserData(
        id = id,
        username = username,
        email = email,
        firstName = firstName,
        lastName = lastName,
        gender = gender,
        image = image,
        token = token
    )
}

fun UserData.mapToUserDomain(): User {
    return User(
        id = id,
        username = username,
        email = email,
        firstName = firstName,
        lastName = lastName,
        gender = gender,
        image = image,
        token = token
    )
}