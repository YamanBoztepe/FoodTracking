package com.example.foodtracking.model.Network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    private val BASE_URL = "https://api.spoonacular.com/"
    val API_KEY = "0691f1158708470d9413b4b4e8196088"

    fun getFood(): FoodByNutrients {
        val retrofitBuilder = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofitBuilder.create(FoodByNutrients::class.java)
    }

    fun getFoodInformation(): Recipe {
        val retrofitBuilder = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofitBuilder.create(Recipe::class.java)
    }
}