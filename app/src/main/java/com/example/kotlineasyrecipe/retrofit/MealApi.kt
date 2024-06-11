package com.example.kotlineasyrecipe.retrofit;

import com.example.kotlineasyrecipe.models.MealList;

import retrofit2.Call;
import retrofit2.http.GET;

interface MealApi {
    @GET("random.php")
    fun getRandomMeal():Call<MealList>
}
