package com.example.foodtracking.model

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.foodtracking.R
import com.example.foodtracking.view.DetailActivity
import com.squareup.picasso.Picasso


class FoodAdapter(val foodList: MutableList<Food>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    val FOOD_VIEW_TYPE = 0
    val FOOTER_VIEW_TYPE = 1

    class FoodHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val txtTitle = itemView.findViewById<TextView>(R.id.txtFoodItemTitle)
        val imgFood = itemView.findViewById<ImageView>(R.id.imgFoodItem)
        val txtCal = itemView.findViewById<TextView>(R.id.txtItemCal)
        val txtProtein = itemView.findViewById<TextView>(R.id.txtItemProtein)
        val txtCarb = itemView.findViewById<TextView>(R.id.txtItemCarb)
        val txtButter = itemView.findViewById<TextView>(R.id.txtItemButter)
    }

    class FooterHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.food_item,parent,false)
        when(viewType) {
            FOOD_VIEW_TYPE -> {
                return FoodHolder(view)
            }
            FOOTER_VIEW_TYPE -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.footer,parent,false)
                return FooterHolder(view)
            }
        }
        return FoodHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is FoodHolder) {
            val food = foodList.get(position)
            holder.txtTitle.text = food.title
            holder.txtCal.text = food.calories.toString() + "kcal "
            holder.txtProtein.text = food.protein + " "
            holder.txtCarb.text = food.carbs + " "
            holder.txtButter.text = food.fat + " "
            Picasso.get().load(food.image).into(holder.imgFood)
            holder.itemView.setOnClickListener {
                val intent = Intent(holder.itemView.context, DetailActivity::class.java)
                intent.putExtra("foodID",food.id)
                holder.itemView.context.startActivity(intent)
            }
        } else if (holder is FooterHolder) {

        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == foodList.size) FOOTER_VIEW_TYPE else FOOD_VIEW_TYPE
    }

    override fun getItemCount(): Int {
        return foodList.size + 1
    }
}