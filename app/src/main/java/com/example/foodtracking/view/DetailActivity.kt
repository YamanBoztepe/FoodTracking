package com.example.foodtracking.view

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.foodtracking.R
import com.example.foodtracking.model.Database.CookedFood
import com.example.foodtracking.model.Database.FoodDatabase
import com.example.foodtracking.model.Macros
import com.example.foodtracking.view.Fragment.MacroFragment
import com.example.foodtracking.view.Fragment.RecipeFragment
import com.example.foodtracking.viewModel.DetailViewModel
import com.squareup.picasso.Picasso

class DetailActivity : AppCompatActivity() {
    private val viewModel = DetailViewModel()
    private var foodID = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        fetchData()
    }

    private fun fetchData() {
        foodID = intent.getSerializableExtra("foodID") as Int
        viewModel.fetchData(foodID) {
            if (it) {
                bindViews()
            }
        }
    }

    private fun bindViews() {
        val txtTitle = findViewById<TextView>(R.id.txtDetailTitle)
        txtTitle.text = viewModel.foodDetails.title

        val imgFoodDetails = findViewById<ImageView>(R.id.imgFoodDetails)
        Picasso.get().load(viewModel.foodDetails.image).into(imgFoodDetails)

        setActions()
    }

    private fun setActions() {
        setRecipeButton()
        setMacroButton()
        setDoneButton()
    }

    private fun setRecipeButton() {
        val recipeButton = findViewById<Button>(R.id.btnRecipe)
        recipeButton.setOnClickListener {
            val recipeFragment = RecipeFragment()
            val bundle = Bundle()
            bundle.putString("recipeText", viewModel.getRecipeText())
            bundle.putString("ingredientsText", viewModel.getIngredientsText())
            recipeFragment.arguments = bundle
            changeFragment(recipeFragment)
        }
    }

    private fun setMacroButton() {
        val macroButton = findViewById<Button>(R.id.btnMacros)
        macroButton.setOnClickListener {
            val macroFragment = MacroFragment()
            val bundle = Bundle()
            bundle.putString("microText", viewModel.getMacroText())
            macroFragment.arguments = bundle
            changeFragment(macroFragment)
        }
    }

    private fun setDoneButton() {
        val doneButton = findViewById<Button>(R.id.btnFoodDone)
        doneButton.setOnClickListener {
            val userMacros = getUserMacro()
            val newUserMacros = viewModel.addDailyMacro(userMacros)
            saveUserMacro(newUserMacros)
            saveInRoom()
            finish()
        }
    }

    private fun changeFragment(fragment: Fragment) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frameLayoutDetails, fragment).commit()
    }

    private fun getUserMacro(): Macros {
        val sharedPreferences = getSharedPreferences("com.example.foodtracking", Context.MODE_PRIVATE)
        val calorie = sharedPreferences.getInt("calorie", 0)
        val protein = sharedPreferences.getInt("protein", 0)
        val fat = sharedPreferences.getInt("fat", 0)
        val carbohydrate = sharedPreferences.getInt("carbohydrate", 0)
        return  Macros(calorie,protein,carbohydrate,fat)
    }

    private fun saveUserMacro(macro: Macros) {
        val sharedPreferences = getSharedPreferences("com.example.foodtracking", Context.MODE_PRIVATE)
        sharedPreferences.edit().putInt("calorie", macro.calorie).apply()
        sharedPreferences.edit().putInt("protein", macro.protein).apply()
        sharedPreferences.edit().putInt("fat", macro.fat).apply()
        sharedPreferences.edit().putInt("carbohydrate", macro.carbonhydrate).apply()
    }

    private fun saveInRoom() {
        val foodDatabase = FoodDatabase.getFoodDatabase(this)
        val foodDetail = viewModel.foodDetails
        val cookedFood = CookedFood(foodID,foodDetail.title,foodDetail.image)
        foodDatabase?.studentDao()?.insert(cookedFood)
    }
}