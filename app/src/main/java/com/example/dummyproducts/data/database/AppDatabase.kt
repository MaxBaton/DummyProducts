package com.example.dummyproducts.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.dummyproducts.data.database.products.ProductDao
import com.example.dummyproducts.data.database.user.UserDao
import com.example.dummyproducts.data.products.storage.models.ProductData
import com.example.dummyproducts.data.user.storage.models.UserData

@Database(
    entities = [UserData::class, ProductData::class],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase: RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun productDao(): ProductDao

    companion object {
        private var INSTANCE: AppDatabase? = null
        private const val DB_NAME = "DummyProductsDb"

        fun getDatabase(context: Context): AppDatabase {
            if (INSTANCE == null) {
                synchronized(AppDatabase::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(
                            context,
                            AppDatabase::class.java,
                            DB_NAME
                        ).build()
                    }
                }
            }

            return INSTANCE!!
        }
    }
}