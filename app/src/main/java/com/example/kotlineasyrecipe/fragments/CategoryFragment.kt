package com.example.kotlineasyrecipe.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.kotlineasyrecipe.activities.CategoryMealsActivity
import com.example.kotlineasyrecipe.activities.MainActivity
import com.example.kotlineasyrecipe.adapter.CategoryListAdapter
import com.example.kotlineasyrecipe.databinding.FragmentCategoryBinding
import com.example.kotlineasyrecipe.models.Category
import com.example.kotlineasyrecipe.viewModel.HomeViewModel

class CategoryFragment : Fragment() {
    private lateinit var binding: FragmentCategoryBinding
    private lateinit var categoryAdaptor: CategoryListAdapter
    private lateinit var homeMvvm: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCategoryBinding.inflate(inflater, container, false)

        categoryAdaptor = CategoryListAdapter()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        homeMvvm = (activity as MainActivity).homeMvvm

        setUpCategoryRecycleView()
        onObserveCategory()
        setUpOnClickItem()
    }

    private fun setUpCategoryRecycleView() {
        binding.rvCategory.apply {
            adapter = categoryAdaptor
            layoutManager = GridLayoutManager(context, 3, GridLayoutManager.VERTICAL, false)
        }
    }

    private fun setUpOnClickItem() {
        categoryAdaptor.onClickItem = { it ->
            val intent = Intent(activity, CategoryMealsActivity::class.java)
            intent.putExtra(HomeFragment.CATEGORY_NAME, it.strCategory)
            startActivity(intent)
        }
    }

    private fun onObserveCategory() {
        homeMvvm.observeCategoryListLiveData().observe(viewLifecycleOwner) {
            categoryAdaptor.setList(it as ArrayList<Category>)
        }
    }

}