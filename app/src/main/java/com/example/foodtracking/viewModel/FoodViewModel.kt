package com.example.foodtracking.viewModel

import android.content.Intent
import com.example.foodtracking.model.Food
import com.example.foodtracking.model.Gender
import com.example.foodtracking.model.Macros
import com.example.foodtracking.model.Network.ApiClient
import com.example.foodtracking.model.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FoodViewModel {
    lateinit var user: User
    lateinit var foods: MutableList<Food>

    var filteredMacros = Macros(0,0,0,0)
    var searchTapped = false
    var isLoading = false

    fun calculateMacrosFor(user: User): Macros {
        return when (user.gender) {
            Gender.Male -> {
                val cal = 10 * user.weight + 6.25 * user.height - 5 * user.age + 5
                val protein = 1.5 * user.weight
                val fat = (cal * 0.15) / 9
                val carbonhydrate = (cal - (cal * 0.15 + protein * 4)) / 4
                Macros(cal.toInt(),protein.toInt(),carbonhydrate.toInt(),fat.toInt())
            }
            Gender.Female -> {
                val cal = 10 * user.weight + 6.25 * user.height - 5 * user.age - 161
                val protein = 1.2 * user.weight
                val fat = (cal * 0.10) / 9
                val carbonhydrate = (cal - (cal * 0.10 + protein * 4)) / 4
                Macros(cal.toInt(),protein.toInt(),carbonhydrate.toInt(),fat.toInt())
            }
        }
    }

    fun loadData(intent: Intent) {
        user = intent.getSerializableExtra("user") as User
        foods = intent.getSerializableExtra("foods") as  MutableList<Food>
    }

    fun fetchData(callback: (success: Boolean) -> Unit) {
        if (!isLoading) {
            isLoading = true
            ApiClient
                .getFood()
                .getFoodByNutrients(ApiClient.API_KEY,
                    10,
                    true,
                    filteredMacros.carbonhydrate,
                    filteredMacros.protein,
                    filteredMacros.fat)
                .enqueue(object: Callback<List<Food>> {
                    override fun onResponse(call: Call<List<Food>>, response: Response<List<Food>>) {
                        if (response.isSuccessful) {
                            var newFoods = response.body() ?: foods
                            if ((filteredMacros.protein != 0 ||
                                filteredMacros.carbonhydrate != 0 ||
                                filteredMacros.fat != 0) &&
                                searchTapped) {
                                    foods.clear()
                                    searchTapped = false
                            }

                            for (newFood in newFoods) {
                                if (!foods.contains(newFood)) {
                                    foods.add(newFood)
                                }
                            }

                            callback(true)
                        }
                        isLoading = false
                    }

                    override fun onFailure(call: Call<List<Food>>, t: Throwable) {
                        isLoading = false
                        callback(false)
                        t.printStackTrace()
                    }
                })
        }
    }
}