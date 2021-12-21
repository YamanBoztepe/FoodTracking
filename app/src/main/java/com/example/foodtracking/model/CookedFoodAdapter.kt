package com.example.foodtracking.model

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.RecyclerView
import com.example.foodtracking.R
import com.example.foodtracking.model.Database.CookedFood
import com.example.foodtracking.view.DetailActivity
import com.squareup.picasso.Picasso

class CookedFoodAdapter(val cookedFoods: ArrayList<CookedFood>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    class CookedFoodHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val txtTitle = itemView.findViewById<TextView>(R.id.txtFoodItemTitle)
        val imgFood = itemView.findViewById<ImageView>(R.id.imgFoodItem)
        val lytMacro = itemView.findViewById<LinearLayout>(R.id.lytMacros)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.food_item,parent,false)
        return CookedFoodHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is CookedFoodHolder) {
            val cookedFood = cookedFoods.get(position)
            holder.txtTitle.text = cookedFood.title
            Picasso.get().load(cookedFood.image).into(holder.imgFood)
            holder.lytMacro.visibility = View.INVISIBLE
            holder.itemView.setOnClickListener {
                val intent = Intent(holder.itemView.context, DetailActivity::class.java)
                intent.putExtra("foodID",cookedFood.foodID)
                holder.itemView.context.startActivity(intent)
            }
        }
    }

    override fun getItemCount(): Int {
        return cookedFoods.size
    }

}