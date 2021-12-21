package com.example.foodtracking.model.Database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface FoodDao {

    @Insert
    fun insert(student: CookedFood)

    @Delete
    fun delete(student: CookedFood)

    @Query("SELECT * FROM cookedfood")
    fun getAllFoods(): List<CookedFood>
}