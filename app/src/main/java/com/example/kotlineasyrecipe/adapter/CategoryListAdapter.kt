package com.example.kotlineasyrecipe.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.kotlineasyrecipe.databinding.CategoryItemsBinding
import com.example.kotlineasyrecipe.models.Category

class CategoryListAdapter() : RecyclerView.Adapter<CategoryListAdapter.CategoryListViewHolder>() {
    var categoryList = ArrayList<Category>()
    lateinit var onClickItem: ((Category) -> Unit)

    fun setList(categoryList: ArrayList<Category>) {
        this.categoryList = categoryList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryListViewHolder {
        return CategoryListViewHolder(
            CategoryItemsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }

    override fun onBindViewHolder(holder: CategoryListViewHolder, position: Int) {
        Glide.with(holder.itemView).load(categoryList[position].strCategoryThumb)
            .into(holder.binding.imgCategoryItem)
        holder.binding.tvCategoryName.text = categoryList[position].strCategory

        holder.binding.categoryItem.setOnClickListener {
            onClickItem.invoke(categoryList[position])
        }
    }

    inner class CategoryListViewHolder(val binding: CategoryItemsBinding) :
        RecyclerView.ViewHolder(binding.root)

}