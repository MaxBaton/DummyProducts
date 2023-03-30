package com.example.dummyproducts.data.database.user

import androidx.room.Dao
import androidx.room.Query
import com.example.dummyproducts.data.user.storage.models.UserData

@Dao
interface UserDao {
    @Query("select * from userData where id = (select max(id) from userData)")
    fun getLastUser(): UserData
}