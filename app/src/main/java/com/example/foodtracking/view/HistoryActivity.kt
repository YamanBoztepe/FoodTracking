package com.example.foodtracking.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.foodtracking.R
import com.example.foodtracking.model.CookedFoodAdapter
import com.example.foodtracking.model.Database.CookedFood
import com.example.foodtracking.model.Database.FoodDatabase
import com.example.foodtracking.model.FoodAdapter

class HistoryActivity : AppCompatActivity() {
    private lateinit var cookedFoods: ArrayList<CookedFood>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)
        loadData()
        bindViews()
    }

    private fun loadData() {
        val foodDatabase = FoodDatabase.getFoodDatabase(this)
        cookedFoods = foodDatabase?.studentDao()?.getAllFoods() as ArrayList<CookedFood>
    }

    private fun bindViews() {
        setRecyclerView()
    }

    private fun setRecyclerView() {
        val recyclerView: RecyclerView = findViewById(R.id.historyRecyclerView)
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        val cookedFoodAdapter = CookedFoodAdapter(cookedFoods)
        recyclerView.adapter = cookedFoodAdapter
    }
}