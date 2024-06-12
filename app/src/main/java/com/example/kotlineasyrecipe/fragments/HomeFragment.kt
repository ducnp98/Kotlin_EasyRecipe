package com.example.kotlineasyrecipe.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.example.kotlineasyrecipe.activities.MealActivity
import com.example.kotlineasyrecipe.adapter.MostPopularAdapter
import com.example.kotlineasyrecipe.databinding.FragmentHomeBinding
import com.example.kotlineasyrecipe.models.CategoryMeal
import com.example.kotlineasyrecipe.models.Meal
import com.example.kotlineasyrecipe.viewModel.HomeViewModel

class HomeFragment : Fragment() {
    private lateinit var homeMvvm: HomeViewModel
    private lateinit var binding: FragmentHomeBinding
    private lateinit var randomMeal: Meal
    private lateinit var popularMealAdapter: MostPopularAdapter

    companion object {
        const val MEAL_ID = "com.example.kotlineasyrecipe.fragments.idMeal"
        const val MEAL_NAME = "com.example.kotlineasyrecipe.fragments.nameMeal"
        const val MEAL_THUMB = "com.example.kotlineasyrecipe.fragments.thumbMeal"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeMvvm = ViewModelProvider(this)[HomeViewModel::class.java]
        popularMealAdapter = MostPopularAdapter()
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

        homeMvvm.getRandomMeal()
        observeRandomMeal()
        homeMvvm.getPopularItems("Seafood")
        observePopularMeal()
        onMealClick()
        onPopularItemClick()
    }

    private fun preparePopularMealAdapter() {
        binding.recViewMealsPopular.apply {
            adapter = popularMealAdapter
            layoutManager = GridLayoutManager(context, 1, GridLayoutManager.HORIZONTAL, false)
        }
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


    private fun onMealClick() {
        binding.randomMeal.setOnClickListener {
            val intent = Intent(activity, MealActivity::class.java)
            intent.putExtra(MEAL_ID, randomMeal.idMeal)
            intent.putExtra(MEAL_NAME, randomMeal.strMeal)
            intent.putExtra(MEAL_THUMB, randomMeal.strMealThumb)
            startActivity(intent)
        }
    }

    private fun observeRandomMeal() {
        homeMvvm.observeRandomMealLiveData().observe(
            viewLifecycleOwner
        ) { value ->
            Glide.with(this@HomeFragment)
                .load(value.strMealThumb)
                .into(binding.imgRandomMeal)

            randomMeal = value
        }
    }

    private fun observePopularMeal() {
        homeMvvm.observePopularMealLiveData().observe(
            viewLifecycleOwner
        ) { value ->
            popularMealAdapter.setMeals(mealList = value as ArrayList<CategoryMeal>)
        }
    }
}