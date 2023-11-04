package com.sg.challengechap2.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.sg.challengechap2.R
import com.sg.challengechap2.databinding.FragmentFoodListBinding
import com.sg.challengechap2.model.Food
import com.sg.challengechap2.presentation.detail.DetailActivity
import com.sg.challengechap2.presentation.home.adapter.category.CategoryListAdapter
import com.sg.challengechap2.presentation.home.adapter.food.FoodListAdapter
import com.sg.challengechap2.presentation.main.MainViewModel
import com.sg.challengechap2.utils.proceedWhen
import org.koin.androidx.viewmodel.ext.android.viewModel

class FoodListFragment : Fragment() {

    private lateinit var binding: FragmentFoodListBinding

    private val categoryAdapter: CategoryListAdapter by lazy {
        CategoryListAdapter {
            foodViewModel.getFoods(it.slug)
        }
    }
    private val foodAdapter: FoodListAdapter by lazy {
        FoodListAdapter(AdapterLayoutMode.LINEAR) { food: Food ->
            navigateToDetail(food)
        }
    }

    private val foodViewModel: FoodViewModel by viewModel()

    private val dataStoreViewModel: MainViewModel by viewModel()
    private fun navigateToDetail(food: Food) {
        DetailActivity.startActivity(requireContext(), food)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFoodListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupCategoryRecyclerview()
        setupFoodRecyclerView()
        toggleLayoutMode()
        setUsername()
        getData()
    }

    private fun getData() {
        foodViewModel.getFoods()
        foodViewModel.getCategories()
    }

    private fun setObserveDataFood() {
        foodViewModel.foodData.observe(viewLifecycleOwner) {
            it.proceedWhen(
                doOnSuccess = { result ->
                    binding.layoutState.root.isVisible = false
                    binding.layoutState.pbLoading.isVisible = false
                    binding.layoutState.tvError.isVisible = false
                    binding.rvFoodList.isVisible = true
                    result.payload?.let {
                        foodAdapter.submitList(it)
                    }
                },
                doOnLoading = {
                    binding.layoutState.root.isVisible = true
                    binding.layoutState.pbLoading.isVisible = true
                    binding.layoutState.tvError.isVisible = false
                    binding.rvFoodList.isVisible = false
                },
                doOnError = { err ->
                    binding.layoutState.root.isVisible = true
                    binding.layoutState.pbLoading.isVisible = false
                    binding.layoutState.tvError.isVisible = true
                    binding.layoutState.tvError.text = err.exception?.message.orEmpty()
                    binding.rvFoodList.isVisible = false
                },
                doOnEmpty = {
                    binding.layoutState.root.isVisible = true
                    binding.layoutState.pbLoading.isVisible = false
                    binding.layoutState.tvError.isVisible = true
                    binding.layoutState.tvError.text = getString(R.string.no_value)
                    binding.rvFoodList.isVisible = false
                }
            )
        }
    }

    private fun setupCategoryRecyclerview() {
        binding.rvCategoryList.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = this@FoodListFragment.categoryAdapter
        }
        setObserveDataCategory()
    }

    private fun setupFoodRecyclerView() {
        val span = if (foodAdapter.adapterLayoutMode == AdapterLayoutMode.LINEAR) 1 else 2
        binding.rvFoodList.apply {
            layoutManager = GridLayoutManager(requireContext(), span)
            adapter = this@FoodListFragment.foodAdapter
        }
        setObserveDataFood()
    }

    private fun toggleLayoutMode() {
        dataStoreViewModel.userLinearLayoutLiveData.observe(viewLifecycleOwner) {
            binding.buttonSwitchMode.isChecked = it
        }

        binding.buttonSwitchMode.setOnCheckedChangeListener { _, isUsingLinear ->
            dataStoreViewModel.setLinearLayoutPref(isUsingLinear)
            (binding.rvFoodList.layoutManager as GridLayoutManager).spanCount = if (isUsingLinear) 2 else 1
            foodAdapter.adapterLayoutMode = if (isUsingLinear) AdapterLayoutMode.GRID else AdapterLayoutMode.LINEAR
            setObserveDataFood()
        }
    }
    private fun setUsername() {
        val fullName = foodViewModel.getCurrentUser()?.fullName
        binding.tvHeaderName.setText("$fullName")
    }

    private fun setObserveDataCategory() {
        foodViewModel.categories.observe(viewLifecycleOwner) {
            it.proceedWhen(
                doOnSuccess = { result ->
                    binding.layoutStateCategory.root.isVisible = false
                    binding.layoutStateCategory.pbLoading.isVisible = false
                    binding.layoutStateCategory.tvError.isVisible = false
                    binding.rvCategoryList.isVisible = true
                    result.payload?.let {
                        categoryAdapter.setData(it)
                    }
                },
                doOnLoading = {
                    binding.layoutStateCategory.root.isVisible = true
                    binding.layoutStateCategory.pbLoading.isVisible = true
                    binding.layoutStateCategory.tvError.isVisible = false
                    binding.rvCategoryList.isVisible = false
                },
                doOnError = { err ->
                    binding.layoutStateCategory.root.isVisible = true
                    binding.layoutStateCategory.pbLoading.isVisible = false
                    binding.layoutStateCategory.tvError.isVisible = true
                    binding.layoutStateCategory.tvError.text = err.exception?.message.orEmpty()
                    binding.rvCategoryList.isVisible = false
                },
                doOnEmpty = {
                    binding.layoutStateCategory.root.isVisible = true
                    binding.layoutStateCategory.pbLoading.isVisible = false
                    binding.layoutStateCategory.tvError.isVisible = true
                    binding.layoutStateCategory.tvError.text = getString(R.string.no_value)
                    binding.rvCategoryList.isVisible = false
                }
            )
        }
    }
}
