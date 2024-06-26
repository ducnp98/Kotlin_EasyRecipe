package com.example.kotlineasyrecipe.retrofit;

import com.example.kotlineasyrecipe.models.CategoryList
import com.example.kotlineasyrecipe.models.CategoryMealList
import com.example.kotlineasyrecipe.models.Meal
import com.example.kotlineasyrecipe.models.MealList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query

interface MealApi {
    @GET("random.php")
    fun getRandomMeal(): Call<MealList>

    @GET("lookup.php?")
    fun getRandomMealDetail(@Query("i") id: String): Call<MealList>

    @GET("filter.php?")
    fun getPopularItem(@Query("c") c: String): Call<CategoryMealList>
   
    @GET("categories.php")
    fun getCategoryList(): Call<CategoryList>

    @GET("lookup.php")
    fun getMealById(@Query("i") id: String): Call<MealList>

}
