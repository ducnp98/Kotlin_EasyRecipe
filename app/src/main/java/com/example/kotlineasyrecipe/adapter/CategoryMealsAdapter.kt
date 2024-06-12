package com.example.kotlineasyrecipe.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.kotlineasyrecipe.databinding.CategoryMealItemBinding
import com.example.kotlineasyrecipe.models.CategoryMeal

class CategoryMealsAdapter() :
    RecyclerView.Adapter<CategoryMealsAdapter.CategoryMealsViewHolder>() {
    private  var listCateMeal = ArrayList<CategoryMeal>()
    lateinit var onClickItem: ((CategoryMeal) -> Unit)

    fun setList(listCateMeal: ArrayList<CategoryMeal>) {
        this.listCateMeal = listCateMeal
        notifyDataSetChanged()
    }

    inner class CategoryMealsViewHolder(val binding: CategoryMealItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryMealsViewHolder {
        return CategoryMealsViewHolder(
            CategoryMealItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return listCateMeal.size
    }

    override fun onBindViewHolder(holder: CategoryMealsViewHolder, position: Int) {
        Glide.with(holder.itemView).load(listCateMeal[position].strMealThumb)
            .into(holder.binding.imgCateItem)
        holder.binding.tvCateMealName.text = listCateMeal[position].strMeal
        holder.binding.cardItem.setOnClickListener {
            onClickItem.invoke(listCateMeal[position])
        }
    }
}