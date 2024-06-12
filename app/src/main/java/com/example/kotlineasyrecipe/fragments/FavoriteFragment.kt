package com.example.kotlineasyrecipe.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.kotlineasyrecipe.R
import com.example.kotlineasyrecipe.activities.MainActivity
import com.example.kotlineasyrecipe.models.Meal
import com.example.kotlineasyrecipe.viewModel.HomeViewModel

class FavoriteFragment : Fragment() {
    private lateinit var viewModel: HomeViewModel
    lateinit var favoriteMeal: List<Meal>;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = (activity as MainActivity).homeMvvm
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeFavoriteMeal();
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_favorite, container, false)
    }

    private fun observeFavoriteMeal() {
        viewModel.observeFavoriteMealLiveData().observe(viewLifecycleOwner) { value ->
            Log.i("dataaa", value.size.toString())
        }
    }
}