package com.example.dummyproducts.data.database

import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.dummyproducts.data.database.products.ProductDao
import com.example.dummyproducts.data.database.user.UserDao
import com.example.dummyproducts.data.products.storage.models.ProductData
import com.example.dummyproducts.data.user.storage.models.UserData

@Database(
    entities = [UserData::class, ProductData::class],
    version = 2,
    exportSchema = true
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
                        )
                            .addMigrations(MIGRATION_1_2)
                            .build()
                    }
                }
            }

            return INSTANCE!!
        }

        private val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL(
                    "create table ProductData (" +
                        "brand VARCHAR(20) not null," +
                        "category VARCHAR(20) not null," +
                        "description VARCHAR(255) not null," +
                        "discountPercentage REAL(2) not null," +
                        "id INT primary key not null," +
                        "images VARCHAR(255) not null," +
                        "price INT not null," +
                        "rating REAL(2) not null," +
                        "stock INT not null," +
                        "thumbnail VARCHAR(255) not null," +
                        "title VARCHAR(32) not null" +
                        ")")
            }
        }
    }
}