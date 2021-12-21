package com.example.foodtracking.view.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.foodtracking.R

class RecipeFragment : Fragment() {
    private lateinit var fragmentView: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentView = inflater.inflate(R.layout.fragment_recipe, container, false)
        bindViews()
        return fragmentView
    }

    private fun bindViews() {
        val txtRecipe = fragmentView.findViewById<TextView>(R.id.txtRecipe)
        val recipeText = arguments!!.getString("recipeText")
        txtRecipe.text = recipeText

        val txtIngredient = fragmentView.findViewById<TextView>(R.id.txtIngredient)
        val ingredientsText = arguments!!.getString("ingredientsText")
        txtIngredient.text = ingredientsText
    }
}