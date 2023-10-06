package com.sg.challengechap2.presentation.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.sg.challengechap2.R
import com.sg.challengechap2.data.CategoryDataSource
import com.sg.challengechap2.data.CategoryDataSourceImpl
import com.sg.challengechap2.data.FoodDataSource
import com.sg.challengechap2.data.FoodDataSourceImpl
import com.sg.challengechap2.databinding.FragmentFoodListBinding
import com.sg.challengechap2.model.CategoryFood
import com.sg.challengechap2.model.Food
import com.sg.challengechap2.presentation.home.adapter.AdapterLayoutMode
import com.sg.challengechap2.presentation.home.adapter.CategoryListAdapter
import com.sg.challengechap2.presentation.home.adapter.FoodListAdapter


class FoodListFragment : Fragment() {

    private lateinit var binding: FragmentFoodListBinding

    private val categoryDataSource: CategoryDataSource by lazy {
        CategoryDataSourceImpl()
    }


    private val categoryAdapter: CategoryListAdapter by lazy {
        CategoryListAdapter(categoryDataSource) {
            clickCategory(it)
        }
    }

    private fun clickCategory(category: CategoryFood) {
        val categoryName = category.categoryName
        Toast.makeText(requireContext(), "$categoryName", Toast.LENGTH_SHORT).show()
    }

    private val foodDataSource: FoodDataSource by lazy {
        FoodDataSourceImpl()
    }

    private val foodAdapter: FoodListAdapter by lazy {
        FoodListAdapter(AdapterLayoutMode.LINEAR) { food: Food ->
            //navigateToDetail(food)
        }
    }

/*    private fun navigateToDetail(food: Food) {
        val action = FoodListFragmentDirections.actionFoodListFragmentToDetailFragment(food)
        findNavController().navigate(action)
    }*/


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFoodListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupCategoryRecyclerview()
        setupFoodRecyclerView()
        setupSwitch()
    }

    private fun setupCategoryRecyclerview() {
        binding.rvCategoryList.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = this@FoodListFragment.categoryAdapter
        }
    }

    private fun setupFoodRecyclerView() {
        val span = if (foodAdapter.adapterLayoutMode == AdapterLayoutMode.LINEAR) 1 else 2
        binding.rvFoodList.apply {
            layoutManager = GridLayoutManager(requireContext(), span)
            adapter = this@FoodListFragment.foodAdapter
        }
        foodAdapter.submitList(foodDataSource.getFoodData())
    }

    private fun setupSwitch() {
        binding.buttonSwitchMode.setOnClickListener {
            toggleLayoutMode()
        }
    }

    private fun toggleLayoutMode() {
        when (foodAdapter.adapterLayoutMode) {
            AdapterLayoutMode.LINEAR -> {
                binding.buttonSwitchMode.setImageResource(R.drawable.ic_list_menu)
                (binding.rvFoodList.layoutManager as GridLayoutManager).spanCount = 2
                foodAdapter.adapterLayoutMode = AdapterLayoutMode.GRID
            }

            AdapterLayoutMode.GRID -> {
                binding.buttonSwitchMode.setImageResource(R.drawable.ic_grid_menu)
                (binding.rvFoodList.layoutManager as GridLayoutManager).spanCount = 1
                foodAdapter.adapterLayoutMode = AdapterLayoutMode.LINEAR
            }
        }
        foodAdapter.refreshList()
    }

}