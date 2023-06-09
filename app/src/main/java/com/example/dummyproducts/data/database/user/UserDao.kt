package com.example.dummyproducts.data.database.user

import androidx.room.*
import com.example.dummyproducts.data.user.storage.models.UserData

@Dao
interface UserDao {
    @Query("select * from userData where id = (select max(id) from userData)")
    fun getLastUser(): UserData

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveUser(userData: UserData)

    @Delete
    fun deleteUser(userData: UserData)
}