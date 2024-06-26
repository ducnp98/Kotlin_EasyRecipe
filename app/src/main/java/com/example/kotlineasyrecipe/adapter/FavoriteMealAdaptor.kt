package com.example.kotlineasyrecipe.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.kotlineasyrecipe.databinding.FavoriteMealBinding
import com.example.kotlineasyrecipe.models.Meal

class FavoriteMealAdaptor() : RecyclerView.Adapter<FavoriteMealAdaptor.FavoriteMealViewHolder>() {
    private val diffUtil=  object: DiffUtil.ItemCallback<Meal>() {
        override fun areItemsTheSame(oldItem: Meal, newItem: Meal): Boolean {
            return oldItem.idMeal == newItem.idMeal
        }

        override fun areContentsTheSame(oldItem: Meal, newItem: Meal): Boolean {
            return oldItem == newItem
        }
    }

    var differ = AsyncListDiffer(this, diffUtil)

    inner class FavoriteMealViewHolder(val binding: FavoriteMealBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteMealViewHolder {
        return FavoriteMealViewHolder(
            FavoriteMealBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: FavoriteMealViewHolder, position: Int) {
        Glide.with(holder.itemView).load(differ.currentList[position].strMealThumb)
            .into(holder.binding.imgFavMeal)
        holder.binding.txtFavMeal.text = differ.currentList[position].strMeal
    }
}