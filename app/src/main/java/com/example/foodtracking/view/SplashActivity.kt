package com.example.foodtracking.view

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.foodtracking.R
import com.example.foodtracking.model.Food
import com.example.foodtracking.model.Gender
import com.example.foodtracking.model.Macros
import com.example.foodtracking.model.Network.ApiClient
import com.example.foodtracking.model.User
import com.example.foodtracking.viewModel.SplashViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList

class SplashActivity : AppCompatActivity() {
    private val viewModel = SplashViewModel()
    private lateinit var foods: ArrayList<Food>
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        startApp()

    }

    private fun startApp() {
        sharedPreferences = getSharedPreferences("com.example.foodtracking", Context.MODE_PRIVATE)
        checkDailyMacros()
        fetchData { directToActivity() }
    }

    private fun fetchData(callback: () -> Unit) {
        viewModel.fetchData {
            it?.let {
                foods = it as ArrayList<Food>
                callback()
            }
        }
    }

    private fun directToActivity() {
        val user = getUser()
        val isUserExist = viewModel.isUserExist(user)
        val activity = if(isUserExist) FoodActivity::class.java else RegisterActivity::class.java
        val intent = Intent(this, activity)
        intent.putExtra("user",user)
        intent.putExtra("foods",foods)
        startActivity(intent)
        finish()
    }

    private fun checkDailyMacros() {
        val hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
        val macrosReset = sharedPreferences.getBoolean("MacrosReset",false)
        if (hour == 0 && !macrosReset) {
            resetMacros()
        } else if (hour != 0 && macrosReset) {
            sharedPreferences.edit().putBoolean("MacrosReset", false).apply()
        }
    }

    private fun getUser(): User {
        val height = sharedPreferences.getInt("height", -1)
        val age = sharedPreferences.getInt("age", -1)
        val weight = sharedPreferences.getFloat("weight", -1.0f).toDouble()
        val genderString = sharedPreferences.getString("gender","null")
        val gender = if (genderString == "Male") Gender.Male else Gender.Female

        return User(height,weight,age,gender)
    }

    private fun resetMacros() {
        sharedPreferences.edit().putInt("calorie", 0).apply()
        sharedPreferences.edit().putInt("protein", 0).apply()
        sharedPreferences.edit().putInt("fat", 0).apply()
        sharedPreferences.edit().putInt("carbohydrate", 0).apply()
        sharedPreferences.edit().putBoolean("MacrosReset", true).apply()
    }
}