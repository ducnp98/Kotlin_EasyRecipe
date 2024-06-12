package com.example.kotlineasyrecipe.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kotlineasyrecipe.db.MealDatabase
import com.example.kotlineasyrecipe.models.Category
import com.example.kotlineasyrecipe.models.CategoryList
import com.example.kotlineasyrecipe.models.CategoryMeal
import com.example.kotlineasyrecipe.models.CategoryMealList
import com.example.kotlineasyrecipe.models.Meal
import com.example.kotlineasyrecipe.models.MealList
import com.example.kotlineasyrecipe.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel(private var mealDatabase: MealDatabase) : ViewModel() {
    private var randomMealLiveData = MutableLiveData<Meal>()
    private var randomPopularItemLiveData = MutableLiveData<List<CategoryMeal>>()
    private var categoryListLiveData = MutableLiveData<List<Category>>()
    private var favoriteMeal = mealDatabase.mealDap().getAllMeals()
    fun getRandomMeal() {
        RetrofitInstance.api.getRandomMeal().enqueue(object : Callback<MealList> {
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                if (response.body() != null) {
                    val randomMeal = response.body()!!.meals[0]
                    randomMealLiveData.value = randomMeal
                } else {
                    return
                }
            }
            override fun onFailure(call: Call<MealList>, t: Throwable) {
                Log.i("Get api meal", "get meal fail ${t.message.toString()}")
            }

        })
    }

    fun getPopularItems(cate: String) {
        RetrofitInstance.api.getPopularItem(cate).enqueue(object : Callback<CategoryMealList> {
            override fun onResponse(
                call: Call<CategoryMealList>,
                response: Response<CategoryMealList>
            ) {
                if (response.body() != null) {
                    randomPopularItemLiveData.value = response.body()!!.meals
                }
            }

            override fun onFailure(call: Call<CategoryMealList>, t: Throwable) {
                Log.i("Get api meal categories", "get meal fail ${t.message.toString()}")
            }

        })
    }

    fun getCategoryList() {
        RetrofitInstance.api.getCategoryList().enqueue(object : Callback<CategoryList> {
            override fun onResponse(call: Call<CategoryList>, response: Response<CategoryList>) {
                if (response.body() != null) {
                    categoryListLiveData.value = response.body()!!.categories
                }
            }

            override fun onFailure(call: Call<CategoryList>, t: Throwable) {
                Log.i("Get api meal categories", "get meal fail ${t.message.toString()}")
            }

        })
    }

    fun observeRandomMealLiveData(): LiveData<Meal> {
        return randomMealLiveData
    }

    fun observePopularMealLiveData(): LiveData<List<CategoryMeal>> {
        return randomPopularItemLiveData
    }

    fun observeCategoryListLiveData(): LiveData<List<Category>> {
        return categoryListLiveData
    }

    fun observeFavoriteMealLiveData(): LiveData<List<Meal>> {
        return favoriteMeal
    }
}