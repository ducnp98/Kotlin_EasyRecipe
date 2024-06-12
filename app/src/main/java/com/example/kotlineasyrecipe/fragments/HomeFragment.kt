package com.example.kotlineasyrecipe.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.example.kotlineasyrecipe.activities.MealActivity
import com.example.kotlineasyrecipe.adapter.CategoryListAdapter
import com.example.kotlineasyrecipe.adapter.MostPopularAdapter
import com.example.kotlineasyrecipe.databinding.FragmentHomeBinding
import com.example.kotlineasyrecipe.models.Category
import com.example.kotlineasyrecipe.models.CategoryMeal
import com.example.kotlineasyrecipe.models.Meal
import com.example.kotlineasyrecipe.viewModel.HomeViewModel

class HomeFragment : Fragment() {
    private lateinit var homeMvvm: HomeViewModel
    private lateinit var binding: FragmentHomeBinding
    private lateinit var randomMeal: Meal
    private lateinit var popularMealAdapter: MostPopularAdapter
    private lateinit var categoryListAdapter: CategoryListAdapter

    companion object {
        const val MEAL_ID = "com.example.kotlineasyrecipe.fragments.idMeal"
        const val MEAL_NAME = "com.example.kotlineasyrecipe.fragments.nameMeal"
        const val MEAL_THUMB = "com.example.kotlineasyrecipe.fragments.thumbMeal"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeMvvm = ViewModelProvider(this)[HomeViewModel::class.java]
        popularMealAdapter = MostPopularAdapter()
        categoryListAdapter = CategoryListAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        preparePopularMealAdapter()
        prepareCategoryListAdapter()

        fetchingRandomMeal()
        fetchingPopularMeal()
        fetchingCategoryList()

        onPopularItemClick()
    }

    private fun preparePopularMealAdapter() {
        binding.recViewMealsPopular.apply {
            adapter = popularMealAdapter
            layoutManager = GridLayoutManager(context, 1, GridLayoutManager.HORIZONTAL, false)
        }
        onMealClick()
    }

    private fun prepareCategoryListAdapter() {
        binding.rvCategoryList.apply {
            adapter = categoryListAdapter
            layoutManager = GridLayoutManager(context, 3, GridLayoutManager.VERTICAL, false)
        }
        onCategoryClick()
    }


    private fun onPopularItemClick() {
        popularMealAdapter.onItemClick = { meal ->
            val intent = Intent(activity, MealActivity::class.java)
            intent.putExtra(MEAL_ID, meal.idMeal)
            intent.putExtra(MEAL_NAME, meal.strMeal)
            intent.putExtra(MEAL_THUMB, meal.strMealThumb)
            startActivity(intent)
        }
    }

    private fun onCategoryClick() {
        categoryListAdapter.onClickItem = { category ->
            Log.i("helloooo", category.toString())
        }
    }

    private fun onMealClick() {
        binding.randomMeal.setOnClickListener {
            val intent = Intent(activity, MealActivity::class.java)
            intent.putExtra(MEAL_ID, randomMeal.idMeal)
            intent.putExtra(MEAL_NAME, randomMeal.strMeal)
            intent.putExtra(MEAL_THUMB, randomMeal.strMealThumb)
            startActivity(intent)
        }
    }

    private fun fetchingRandomMeal() {
        homeMvvm.getRandomMeal()
        homeMvvm.observeRandomMealLiveData().observe(
            viewLifecycleOwner
        ) { value ->
            Glide.with(this@HomeFragment)
                .load(value.strMealThumb)
                .into(binding.imgRandomMeal)

            randomMeal = value
        }
    }


    private fun fetchingPopularMeal() {
        homeMvvm.getPopularItems("Seafood")
        homeMvvm.observePopularMealLiveData().observe(
            viewLifecycleOwner
        ) { value ->
            popularMealAdapter.setMeals(mealList = value as ArrayList<CategoryMeal>)
        }
    }

    private fun fetchingCategoryList() {
        homeMvvm.getCategoryList()
        homeMvvm.observeCategoryListLiveData().observe(viewLifecycleOwner) { value ->
            categoryListAdapter.setList(categoryList = value as ArrayList<Category>)
        }
    }
}