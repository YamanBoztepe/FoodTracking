package com.example.foodtracking.model.Network

import com.example.foodtracking.model.FoodInformations
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Recipe {
    @GET("recipes/{id}/information")
    fun getRecipe(
        @Path("id") id: Int,
        @Query("apiKey") apiKey: String,
        @Query("includeNutrition") includeNutrition: Boolean): Call<FoodInformations>
}