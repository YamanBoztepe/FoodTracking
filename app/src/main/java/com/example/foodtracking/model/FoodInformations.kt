package com.example.foodtracking.model

data class FoodInformations(
    val title: String?,
    val image: String?,
    val servings: Int?,
    val glutenFree: Boolean?,
    val ketogenic: Boolean?,
    val vegan: Boolean?,
    val vegetarian: Boolean?,
    val dishTypes: List<String>?,
    val extendedIngredients: List<Ingredients>?,
    val nutrition: Nutrition?,
    val summary: String?
)

data class Ingredients(
    val aisle: String?,
    val amount: Float?
)

data class Nutrition(
    val nutrients: List<Nutrients>?
)

data class Nutrients(
    val title: String?,
    val amount: Float?,
    val unit: String?
)