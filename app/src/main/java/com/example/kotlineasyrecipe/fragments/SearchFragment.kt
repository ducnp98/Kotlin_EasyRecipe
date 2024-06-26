package com.example.kotlineasyrecipe.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.kotlineasyrecipe.activities.MainActivity
import com.example.kotlineasyrecipe.adapter.SearchListMealAdapter
import com.example.kotlineasyrecipe.databinding.FragmentSearchBinding
import com.example.kotlineasyrecipe.models.Meal
import com.example.kotlineasyrecipe.viewModel.HomeViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearchFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding
    private lateinit var homeMvvm: HomeViewModel
    private lateinit var searchListAdapter: SearchListMealAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        homeMvvm = (activity as MainActivity).homeMvvm

        searchListAdapter = SearchListMealAdapter()

        setUpSearchListMeal()
        observeSearchListMeal()

        binding.btnSearch.setOnClickListener {
        }

        var searchJob: Job? = null
        binding.etMeal.addTextChangedListener {
            searchJob?.cancel()

            searchJob = lifecycleScope.launch {
                delay(500)
                searchItem(it.toString())
            }

        }
    }

    private fun setUpSearchListMeal() {
        binding.rvListMeal.apply {
            adapter = searchListAdapter
            layoutManager =
                GridLayoutManager(this@SearchFragment.context, 3, GridLayoutManager.VERTICAL, false)

        }
    }

    private fun observeSearchListMeal() {
        homeMvvm.observeSearchListMeal().observe(viewLifecycleOwner) {
            searchListAdapter.setList(it as ArrayList<Meal>)
        }
    }

    private fun searchItem(name: String) {
        if (name.isNotEmpty()) {
            homeMvvm.getSearchByMealName(name)
        }
    }
}