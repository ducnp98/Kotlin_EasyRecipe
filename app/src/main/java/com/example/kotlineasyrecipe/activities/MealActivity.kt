package com.example.kotlineasyrecipe.activities

import android.content.Intent
import android.graphics.Typeface
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.kotlineasyrecipe.R
import com.example.kotlineasyrecipe.databinding.ActivityMealBinding
import com.example.kotlineasyrecipe.fragments.HomeFragment
import com.example.kotlineasyrecipe.models.Meal
import com.example.kotlineasyrecipe.viewModel.MealDetailModel

class MealActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMealBinding;
    private lateinit var idMeal: String;
    private lateinit var nameMeal: String;
    private lateinit var thumbMeal: String;
    private lateinit var mealDetailMvvm: MealDetailModel
    private lateinit var mealDetail: Meal

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMealBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mealDetailMvvm = ViewModelProvider(this)[MealDetailModel::class.java]

        getMealFromHome()
        setInformationMeal()
        loadingCase()
        mealDetailMvvm.getMealDetail(idMeal)
        observeMealDetail()
        onYoutubeImageClick()

    }

    private fun onYoutubeImageClick() {
        binding.ivYoutube.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(mealDetail.strYoutube))
            startActivity(intent)
        }
    }


    private fun observeMealDetail() {
        mealDetailMvvm.getObserveMealDetailLiveData().observe(this, object : Observer<Meal> {
            override fun onChanged(value: Meal) {
                mealDetail = value
                binding.tvCategory.setText("Categoty | ${value.strCategory}")
                binding.tvLocation.setText("Area | ${value.strArea}")
                binding.tvDescription.setText(value.strInstructions)

                onResponseCase()
            }
        })
    }

    private fun loadingCase() {
        binding.icFavorite.visibility = View.INVISIBLE
        binding.tvInstruction.visibility = View.INVISIBLE
        binding.tvCategory.visibility = View.INVISIBLE
        binding.tvLocation.visibility = View.INVISIBLE
        binding.tvDescription.visibility = View.INVISIBLE
        binding.ivYoutube.visibility = View.INVISIBLE
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun onResponseCase() {
        binding.icFavorite.visibility = View.VISIBLE
        binding.tvInstruction.visibility = View.VISIBLE
        binding.tvCategory.visibility = View.VISIBLE
        binding.tvLocation.visibility = View.VISIBLE
        binding.tvDescription.visibility = View.VISIBLE
        binding.ivYoutube.visibility = View.VISIBLE
        binding.progressBar.visibility = View.INVISIBLE
    }

    private fun getMealFromHome() {
        val intent = getIntent()
        idMeal = intent.getStringExtra(HomeFragment.MEAL_ID)!!
        nameMeal = intent.getStringExtra(HomeFragment.MEAL_NAME)!!
        thumbMeal = intent.getStringExtra(HomeFragment.MEAL_THUMB)!!
    }

    private fun setInformationMeal() {
        Glide.with(this.applicationContext).load(thumbMeal).into(binding.imgMealDetail)
        binding.collapsingToolBar.title = nameMeal
        binding.collapsingToolBar.setCollapsedTitleTextColor(resources.getColor(R.color.white))
        binding.collapsingToolBar.setExpandedTitleColor(resources.getColor(R.color.white))
        binding.collapsingToolBar.setExpandedTitleTextAppearance(R.style.ExpandedAppBar);
        binding.collapsingToolBar.setCollapsedTitleTextAppearance(R.style.CollapsedAppBar)
    }
}