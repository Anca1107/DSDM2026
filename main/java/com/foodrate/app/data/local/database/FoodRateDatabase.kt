package com.foodrate.app.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.foodrate.app.data.local.dao.UserDao
import com.foodrate.app.data.local.entity.UserEntity

@Database(
    entities = [UserEntity::class],
    version = 1
)
abstract class FoodRateDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

    companion object {
        @Volatile
        private var INSTANCE: FoodRateDatabase? = null

        fun getDatabase(context: Context): FoodRateDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    FoodRateDatabase::class.java,
                    "foodrate_database"
                ).build()

                INSTANCE = instance
                instance
            }
        }
    }
}