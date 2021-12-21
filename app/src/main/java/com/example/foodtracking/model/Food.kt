package com.example.foodtracking.model

import java.io.Serializable

data class Food(
    val id: Int?,
    val title: String?,
    val image: String?,
    val calories: Int?,
    val protein: String?,
    val fat: String?,
    val carbs: String?
    ): Serializable
