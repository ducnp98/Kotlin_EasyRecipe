package com.example.kotlineasyrecipe.retrofit;

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
}
