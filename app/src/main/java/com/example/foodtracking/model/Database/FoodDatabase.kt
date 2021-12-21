package com.example.foodtracking.model.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [CookedFood::class], version = 1)
abstract class FoodDatabase : RoomDatabase() {

    abstract fun studentDao(): FoodDao

    companion object {
        private var instance: FoodDatabase? = null

        fun getFoodDatabase(context: Context): FoodDatabase? {
            if (instance == null) {
                instance = Room.databaseBuilder(
                    context,
                    FoodDatabase::class.java,
                    "cookedfood.db"
                ).allowMainThreadQueries().build()
            }
            return instance
        }
    }
}