package com.example.foodtracking.viewModel

import android.os.Build
import android.text.Html
import android.text.Html.fromHtml
import com.example.foodtracking.model.FoodInformations
import com.example.foodtracking.model.Macros
import com.example.foodtracking.model.Network.ApiClient
import retrofit2.Call
import retrofit2.Response
import retrofit2.Callback

class DetailViewModel {
    lateinit var foodDetails: FoodInformations

    fun fetchData(id: Int, callback: (success: Boolean) -> Unit) {
        ApiClient
            .getFoodInformation()
            .getRecipe(id,ApiClient.API_KEY,true)
            .enqueue(object: Callback<FoodInformations> {
                override fun onResponse(
                    call: Call<FoodInformations>,
                    response: Response<FoodInformations>
                ) {
                    if(response.isSuccessful) {
                        response.body()?.let {
                            foodDetails = it
                        }
                        callback(true)
                    }

                }

                override fun onFailure(call: Call<FoodInformations>, t: Throwable) {
                    callback(false)
                    t.printStackTrace()
                }

            })
    }

    fun getIngredientsText(): String {
        var ingredients = ""
        foodDetails.extendedIngredients?.let {
            it.forEach {
                ingredients += "${it.aisle + " " + "x" + it.amount}\n"
            }
        }
        return  ingredients
    }

    fun getRecipeText(): String {
        var dishText = ""
        foodDetails.dishTypes?.let {
            if (it.isNotEmpty()) dishText += "Dish Types: \n"
            it.forEach { dishText += "$it\n" }
        }

        foodDetails.summary?.let {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                dishText += "\n${fromHtml(it,Html.FROM_HTML_MODE_LEGACY)}"
            } else {
                dishText += "\n${fromHtml(it)}"
            }
        }
        dishText += "\n\n\n"
        return  dishText
    }

    fun getMacroText(): String {
        var macroText = ""
        foodDetails.servings?.let { macroText += "\nServings: $it" }
        foodDetails.glutenFree?.let { macroText += "\nGluten Free: ${if (it) "Yes" else "No"}" }
        foodDetails.ketogenic?.let { macroText += "\nKetogenic: ${if (it) "Yes" else "No"}" }
        foodDetails.vegan?.let { macroText += "\nVegan: ${if (it) "Yes" else "No"}" }
        foodDetails.vegetarian?.let { macroText += "\nVegetarian: ${if (it) "Yes" else "No"}" }
        foodDetails.nutrition?.nutrients?.forEach {
            macroText += "\n${it.title + ":" + " " + it.amount + " " + it.unit}"
        }
        return  macroText
    }

    fun addDailyMacro(macros: Macros): Macros {
        foodDetails.nutrition?.nutrients?.forEach {
            when (it.title) {
                "Calories" -> {
                    macros.calorie += it.amount?.toInt() ?: 0
                }
                "Protein" -> {
                    macros.protein += it.amount?.toInt() ?: 0
                }
                "Fat" -> {
                    macros.fat += it.amount?.toInt() ?: 0
                }
                "Carbohydrates" -> {
                    macros.carbonhydrate += it.amount?.toInt() ?: 0
                }
            }
        }
        return macros
    }
}