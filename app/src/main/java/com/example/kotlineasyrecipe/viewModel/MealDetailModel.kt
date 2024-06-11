package com.example.kotlineasyrecipe.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kotlineasyrecipe.models.Meal
import com.example.kotlineasyrecipe.models.MealList
import com.example.kotlineasyrecipe.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MealDetailModel : ViewModel() {
    private var mealDetail = MutableLiveData<Meal>()
    fun getMealDetail(id: String) {
        RetrofitInstance.api.getRandomMealDetail(id).enqueue(object : Callback<MealList> {
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                if (response.body() != null) {
                    mealDetail.value = response.body()!!.meals[0]
                }
            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
                Log.i("Get meal detail", "${t.message.toString()}")
            }
        })
    }

    fun getObserveMealDetailLiveData(): LiveData<Meal> {
        return mealDetail
    }
}