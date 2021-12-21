package com.example.foodtracking.model.Network

import com.example.foodtracking.model.Food
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface FoodByNutrients {
    @GET("recipes/findByNutrients")
    fun getFoodByNutrients(
        @Query("apiKey") apiKey: String,
        @Query("number") number: Int,
        @Query("random") random: Boolean,
        @Query("minCarbs") minCarbs: Int,
        @Query("minProtein") minProtein: Int,
        @Query("minFat") minFat: Int): Call<List<Food>>
}