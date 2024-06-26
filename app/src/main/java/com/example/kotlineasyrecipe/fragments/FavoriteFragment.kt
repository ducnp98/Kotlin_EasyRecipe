package com.example.kotlineasyrecipe.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlineasyrecipe.activities.MainActivity
import com.example.kotlineasyrecipe.adapter.FavoriteMealAdaptor
import com.example.kotlineasyrecipe.databinding.FragmentFavoriteBinding
import com.example.kotlineasyrecipe.models.Meal
import com.example.kotlineasyrecipe.viewModel.HomeViewModel
import com.google.android.material.snackbar.Snackbar

class FavoriteFragment : Fragment() {
    private lateinit var viewModel: HomeViewModel
    lateinit var favoriteMeal: List<Meal>;
    lateinit var binding: FragmentFavoriteBinding
    lateinit var favMealAdapter: FavoriteMealAdaptor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = (activity as MainActivity).homeMvvm
        favMealAdapter = FavoriteMealAdaptor();
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeFavoriteMeal();
        setUpFavoriteMealAdaptor()
        setItemClick()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun observeFavoriteMeal() {
        viewModel.observeFavoriteMealLiveData().observe(requireActivity()) { value ->
            favoriteMeal = value
            favMealAdapter.differ.submitList(value)
        }
    }

    private fun setUpFavoriteMealAdaptor() {
        binding.rvFavoriteMeal.apply {
            adapter = favMealAdapter
            layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
        }
    }

    private fun setItemClick() {
        val itemTouchHelper = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ) = true

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val meal = favMealAdapter.differ.currentList[position]
                viewModel.deleteFavoriteMeal(meal)

                Snackbar.make(requireView(), "Meal Deleted", Snackbar.LENGTH_LONG).setAction(
                    "Undo"
                ) {
                    viewModel.insertFavoriteMeal(meal)
                }.show()
            }
        }

        ItemTouchHelper(itemTouchHelper).attachToRecyclerView(binding.rvFavoriteMeal)
    }
}