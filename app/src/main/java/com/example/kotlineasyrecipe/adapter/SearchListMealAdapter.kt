package com.example.kotlineasyrecipe.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.kotlineasyrecipe.databinding.CategoryItemsBinding
import com.example.kotlineasyrecipe.models.Meal

class SearchListMealAdapter :
    RecyclerView.Adapter<SearchListMealAdapter.SearchListMealViewHolder>() {

    var mealList = ArrayList<Meal>()

    fun setList(list: ArrayList<Meal>) {
        mealList = list
        notifyDataSetChanged()
    }

    inner class SearchListMealViewHolder(val binding: CategoryItemsBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchListMealViewHolder {
        return SearchListMealViewHolder(
            CategoryItemsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return mealList.size
    }

    override fun onBindViewHolder(holder: SearchListMealViewHolder, position: Int) {
        Glide.with(holder.itemView).load(mealList[position].strMealThumb).into(holder.binding.imgCategoryItem)

        holder.binding.tvCategoryName.text = mealList[position].strMeal
    };
}