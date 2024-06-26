package com.example.kotlineasyrecipe.fragments

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.bumptech.glide.Glide
import com.example.kotlineasyrecipe.R
import com.example.kotlineasyrecipe.activities.MainActivity
import com.example.kotlineasyrecipe.activities.MealActivity
import com.example.kotlineasyrecipe.databinding.FragmentBottomSheetBinding
import com.example.kotlineasyrecipe.models.Meal
import com.example.kotlineasyrecipe.viewModel.HomeViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


private const val ARG_PARAM1 = "param1"

class BottomSheetFragment : BottomSheetDialogFragment() {
    private var mealId: String? = null
    private lateinit var binding: FragmentBottomSheetBinding;
    private lateinit var homeMvvm: HomeViewModel
    private lateinit var mealDetail: Meal

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            mealId = it.getString(ARG_PARAM1)
        }
    }

    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        val window = dialog.window

        window!!.setBackgroundDrawableResource(R.drawable.background_modal_bottom_sheet)
        return dialog
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        homeMvvm = (activity as MainActivity).homeMvvm

        mealId?.let { homeMvvm.getMealById(it) }

        observeMealById()
        setUpOnNavigateDetail()
    }

    private fun observeMealById() {
        homeMvvm.observeMealById().observe(viewLifecycleOwner) {
            binding.txtLocation.text = it.strArea
            binding.txtCategory.text = it.strArea
            Glide.with(this@BottomSheetFragment).load(it.strMealThumb).into(binding.imgModal)
            mealDetail = it
        }
    }

    private fun setUpOnNavigateDetail() {
        binding.modelMeal.setOnClickListener {
            val intent = Intent(activity, MealActivity::class.java)

            intent.putExtra(HomeFragment.MEAL_ID, mealDetail.idMeal.toString())
            intent.putExtra(HomeFragment.MEAL_NAME, mealDetail.strMeal)
            intent.putExtra(HomeFragment.MEAL_THUMB, mealDetail.strMealThumb)

            startActivity(intent)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(mealId: String) =
            BottomSheetFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, mealId)
                }
            }
    }
}