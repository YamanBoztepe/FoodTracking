package com.example.foodtracking.viewModel

import com.example.foodtracking.model.Food
import com.example.foodtracking.model.Network.ApiClient
import com.example.foodtracking.model.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SplashViewModel {
    fun isUserExist(user: User): Boolean {
        return user.height != -1 || user.age != -1
    }

    fun fetchData(callback: (List<Food>?) -> Unit) {
        ApiClient
            .getFood()
            .getFoodByNutrients(ApiClient.API_KEY,10,true,0,0,0)
            .enqueue(object: Callback<List<Food>> {
                override fun onResponse(call: Call<List<Food>>, response: Response<List<Food>>) {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            callback(it)
                        }
                    }
                }

                override fun onFailure(call: Call<List<Food>>, t: Throwable) {
                    callback(null)
                    t.printStackTrace()
                }
            })
    }
}