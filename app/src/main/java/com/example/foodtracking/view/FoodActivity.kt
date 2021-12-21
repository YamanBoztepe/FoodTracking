package com.example.foodtracking.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.updateLayoutParams
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.foodtracking.model.Food
import com.example.foodtracking.model.FoodAdapter
import com.example.foodtracking.R
import com.example.foodtracking.model.Database.CookedFood
import com.example.foodtracking.model.Database.FoodDatabase
import com.example.foodtracking.model.Macros
import com.example.foodtracking.model.User
import com.example.foodtracking.viewModel.FoodViewModel
import java.util.*
import javax.crypto.Mac
import kotlin.collections.ArrayList

class FoodActivity: AppCompatActivity() {
    private val viewModel = FoodViewModel()
    private lateinit var foodAdapter: FoodAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_food)
        viewModel.loadData(intent)
        bindViews()
    }

    override fun onResume() {
        super.onResume()
        setMacroBarView()
    }

    private fun bindViews() {
        setRecyclerView()
        setMacroBarView()
        setActions()
    }

    private fun setRecyclerView() {
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        foodAdapter = FoodAdapter(viewModel.foods)
        recyclerView.adapter = foodAdapter
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val visibleItemCount = layoutManager.childCount
                val pastVisibleItem = layoutManager.findFirstCompletelyVisibleItemPosition()
                val total = foodAdapter.itemCount

                if (!viewModel.isLoading) {
                    if ((visibleItemCount + pastVisibleItem) >= total) {
                        viewModel.fetchData {
                            if (it) {
                                foodAdapter.notifyDataSetChanged()
                            }
                        }
                    }
                }
            }
        })
    }

    private fun setMacroBarView() {
        val txtCal: TextView = findViewById(R.id.txtCal)
        val txtProtein: TextView = findViewById(R.id.txtProtein)
        val txtCarbonhydrate: TextView = findViewById(R.id.txtCarbonhydrate)
        val txtFat: TextView = findViewById(R.id.txtButter)
        val macros = viewModel.calculateMacrosFor(viewModel.user)
        val userMacros = getUserMacro()

        txtCal.text = "${userMacros.calorie}/${macros.calorie}"
        txtProtein.text = "${userMacros.protein}/${macros.protein}"
        txtCarbonhydrate.text = "${userMacros.carbonhydrate}/${macros.carbonhydrate}"
        txtFat.text = "${userMacros.fat}/${macros.fat}"
    }

    private fun setActions() {
        setEditButton()
        setFilterButton()
        setSearchButton()
        setHistoryButton()
    }

    private fun setEditButton() {
        val editButton: Button = findViewById(R.id.editbutton)
        editButton.setOnClickListener {
            directToEditScreen()
        }
    }

    private fun setFilterButton() {
        val filterButton: Button = findViewById(R.id.btnFilter)
        filterButton.setOnClickListener {
            presentFilter()
        }
    }

    private fun setSearchButton() {
        val searchButton: Button = findViewById(R.id.btnSearch)
        searchButton.setOnClickListener {
            viewModel.searchTapped = true
            searchByNutrients()
        }
    }

    private fun setHistoryButton() {
        val historyButton: Button = findViewById(R.id.btnHistory)
        historyButton.setOnClickListener {
            directToHistoryScreen()
        }
    }

    private fun getUserMacro(): Macros {
        val sharedPreferences = getSharedPreferences("com.example.foodtracking", Context.MODE_PRIVATE)
        val calorie = sharedPreferences.getInt("calorie", 0)
        val protein = sharedPreferences.getInt("protein", 0)
        val fat = sharedPreferences.getInt("fat", 0)
        val carbohydrate = sharedPreferences.getInt("carbohydrate", 0)
        return  Macros(calorie,protein,carbohydrate,fat)
    }

    private fun directToEditScreen() {
        val intent = Intent(this, RegisterActivity::class.java)
        intent.putExtra("user",viewModel.user)
        intent.putExtra("foods",viewModel.foods as ArrayList<Food>)
        startActivity(intent)
        finish()
    }

    private fun directToHistoryScreen() {
        val intent = Intent(this, HistoryActivity::class.java)
        startActivity(intent)
    }

    private fun presentFilter() {
        val lytFilter = findViewById<LinearLayout>(R.id.lytFilter)
        lytFilter.updateLayoutParams {
            this.height = if (lytFilter.height > 0) 0 else 200
        }
    }

    private fun searchByNutrients() {
        val txtFilterProtein = findViewById<EditText>(R.id.txtFilterProtein)
        val txtFilterCarb = findViewById<EditText>(R.id.txtFilterCarb)
        val txtFilterFat = findViewById<EditText>(R.id.txtFilterFat)

        val protein = if (txtFilterProtein.text.isEmpty()) "0" else txtFilterProtein.text
        val carb = if (txtFilterCarb.text.isEmpty()) "0" else txtFilterCarb.text
        val fat = if (txtFilterFat.text.isEmpty()) "0" else txtFilterFat.text

        viewModel.filteredMacros = Macros(
            0,
            protein.toString().toInt(),
            carb.toString().toInt(),
            fat.toString().toInt()
        )
        viewModel.fetchData {
            foodAdapter.notifyDataSetChanged()
        }
    }
}