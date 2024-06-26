package com.example.kotlineasyrecipe.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.kotlineasyrecipe.databinding.PopularItemBinding
import com.example.kotlineasyrecipe.models.CategoryMeal

class MostPopularAdapter() : RecyclerView.Adapter<MostPopularAdapter.PopularMealViewHolder>() {
    private var mealList = ArrayList<CategoryMeal>()
    lateinit var onItemClick: ((CategoryMeal) -> Unit)
    var onLongItemClick: ((CategoryMeal) -> Unit)? = null

    override fun onBindViewHolder(holder: PopularMealViewHolder, position: Int) {
        Glide.with(holder.itemView).load(mealList[position].strMealThumb)
            .into(holder.binding.imgPopular)

        holder.itemView.setOnClickListener {
            onItemClick.invoke(mealList[position])
        }

        holder.itemView.setOnLongClickListener {
            onLongItemClick?.invoke(mealList[position])
            true
        }
    }

    override fun getItemCount(): Int {
        return mealList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularMealViewHolder {
        return PopularMealViewHolder(PopularItemBinding.inflate(LayoutInflater.from(parent.context), parent,false))
    }

    fun setMeals(mealList: ArrayList<CategoryMeal>) {
        this.mealList = mealList
        notifyDataSetChanged()
    }

    class PopularMealViewHolder(val binding: PopularItemBinding) : RecyclerView.ViewHolder(binding.root)
}