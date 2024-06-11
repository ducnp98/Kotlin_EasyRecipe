package com.example.kotlineasyrecipe.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.kotlineasyrecipe.databinding.FragmentHomeBinding
import com.example.kotlineasyrecipe.models.Meal
import com.example.kotlineasyrecipe.viewModel.HomeViewModel

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class HomeFragment : Fragment() {
    private lateinit var homeMvvm: HomeViewModel

    lateinit var binding: FragmentHomeBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeMvvm = ViewModelProvider(this)[HomeViewModel::class.java]
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        homeMvvm.getRandomMeal()
        observeRandomMeal()
    }

    private fun observeRandomMeal() {
        homeMvvm.observeRandomMealLiveData().observe(viewLifecycleOwner, object :Observer<Meal> {
            override fun onChanged(value: Meal) {
                Glide.with(this@HomeFragment)
                    .load(value.strMealThumb)
                    .into(binding.imgRandomMeal)
            }

        })
    }
}