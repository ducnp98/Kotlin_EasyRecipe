package com.example.kotlineasyrecipe.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.kotlineasyrecipe.adapter.CategoryMealsAdapter
import com.example.kotlineasyrecipe.databinding.ActivityCategoryMealsBinding
import com.example.kotlineasyrecipe.db.MealDatabase
import com.example.kotlineasyrecipe.fragments.HomeFragment
import com.example.kotlineasyrecipe.models.CategoryMeal
import com.example.kotlineasyrecipe.viewModel.HomeViewModel
import com.example.kotlineasyrecipe.viewModel.HomeViewModelFactory
import com.example.kotlineasyrecipe.viewModel.MealDetailModel

class CategoryMealsActivity : AppCompatActivity() {
    lateinit var cateName: String;
    lateinit var binding: ActivityCategoryMealsBinding
    lateinit var homeMvvm: HomeViewModel
    lateinit var cateMealAdapter: CategoryMealsAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCategoryMealsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        cateMealAdapter = CategoryMealsAdapter()
        initAdapter()

        val mealDatabase = MealDatabase.getInstance(this)
        val viewModelFactory = HomeViewModelFactory(mealDatabase)
        homeMvvm = ViewModelProvider(this, viewModelFactory)[HomeViewModel::class.java]
        getCateNameFromHome();
        getMealsByCategoryName()
        onClickCateItem()
    }

    private fun getCateNameFromHome() {
        val intent = intent
        cateName = intent.getStringExtra(HomeFragment.CATEGORY_NAME)!!
        binding.tvCategoryCount.text = cateName
    }

    private fun getMealsByCategoryName() {
        homeMvvm.getPopularItems(cateName)

        homeMvvm.observePopularMealLiveData().observe(this) {
            it
            cateMealAdapter.setList(listCateMeal = it as ArrayList<CategoryMeal>)
            binding.tvCategoryCount.text = "${cateName} | ${it.size}"
        }
    }

    private fun onClickCateItem () {
        cateMealAdapter.onClickItem = {categoryMeal ->
            val intent = Intent(this, MealActivity::class.java)
            intent.putExtra(HomeFragment.MEAL_ID, categoryMeal.idMeal)
            intent.putExtra(HomeFragment.MEAL_NAME, categoryMeal.strMeal)
            intent.putExtra(HomeFragment.MEAL_THUMB, categoryMeal.strMealThumb)
            startActivity(intent)
        }
    }

    private fun initAdapter() {
        binding.rvCategoryMeals.apply {
            adapter = cateMealAdapter
            layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
        }
    }

}