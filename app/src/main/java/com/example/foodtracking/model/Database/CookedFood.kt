package com.example.foodtracking.model.Database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.foodtracking.model.Nutrition

@Entity(tableName = "cookedfood")
data class CookedFood(
    @PrimaryKey @ColumnInfo(name = "foodID") val foodID: Int,
    @ColumnInfo(name = "title") val title: String?,
    @ColumnInfo(name = "image") val image: String?,
    )