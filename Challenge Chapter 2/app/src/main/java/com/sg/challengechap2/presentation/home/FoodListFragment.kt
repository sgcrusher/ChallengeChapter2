package com.sg.challengechap2.presentation.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.sg.challengechap2.R
import com.sg.challengechap2.data.dummy.CategoryDataSource
import com.sg.challengechap2.data.dummy.CategoryDataSourceImpl
import com.sg.challengechap2.data.dummy.FoodDataSource
import com.sg.challengechap2.data.dummy.FoodDataSourceImpl
import com.sg.challengechap2.data.local.database.AppDatabase
import com.sg.challengechap2.data.local.database.datasource.FoodDatabaseDataSource
import com.sg.challengechap2.data.local.datastore.UserPreferenceDataSourceImpl
import com.sg.challengechap2.data.local.datastore.appDataStore
import com.sg.challengechap2.data.repository.FoodRepository
import com.sg.challengechap2.data.repository.FoodRepositoryImpl
import com.sg.challengechap2.databinding.FragmentFoodListBinding
import com.sg.challengechap2.model.CategoryFood
import com.sg.challengechap2.model.Food
import com.sg.challengechap2.presentation.detail.DetailActivity
import com.sg.challengechap2.presentation.home.adapter.AdapterLayoutMode
import com.sg.challengechap2.presentation.home.adapter.CategoryListAdapter
import com.sg.challengechap2.presentation.home.adapter.FoodListAdapter
import com.sg.challengechap2.presentation.main.MainViewModel
import com.sg.challengechap2.utils.GenericViewModelFactory
import com.sg.challengechap2.utils.PreferenceDataStoreHelperImpl
import com.sg.challengechap2.utils.proceedWhen


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
            navigateToDetail(food)
        }
    }

    private val foodViewModel: FoodViewModel by viewModels {
        // val cds : CategoryDataSource = CategoryDataSourceImpl()
        val database = AppDatabase.getInstance(requireContext())
        val foodDao = database.foodDao()
        val foodDataSource = FoodDatabaseDataSource(foodDao)
        val repo: FoodRepository = FoodRepositoryImpl(foodDataSource)
        GenericViewModelFactory.create(FoodViewModel(repo))
    }

    private val dataStoreViewModel: MainViewModel by viewModels {
        val dataStore = this.requireContext().appDataStore
        val dataStoreHelper = PreferenceDataStoreHelperImpl(dataStore)
        val userPreferenceDataSource = UserPreferenceDataSourceImpl(dataStoreHelper)
        GenericViewModelFactory.create(MainViewModel(userPreferenceDataSource))
    }

    private fun navigateToDetail(food: Food) {
        /*val action = FoodListFragmentDirections.actionFoodListFragmentToDetailFragment(food)
        findNavController().navigate(action)*/
        DetailActivity.startActivity(requireContext(), food)
    }


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

    private fun setObserveData() {
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
                }, doOnEmpty = {
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
    }

    private fun setupFoodRecyclerView() {
        val span = if (foodAdapter.adapterLayoutMode == AdapterLayoutMode.LINEAR) 1 else 2
        binding.rvFoodList.apply {
            layoutManager = GridLayoutManager(requireContext(), span)
            adapter = this@FoodListFragment.foodAdapter
        }
        setObserveData()
    }

    private fun setupSwitch() {
        binding.buttonSwitchMode.setOnClickListener {
            toggleLayoutMode()
        }
    }

    private fun toggleLayoutMode() {
        dataStoreViewModel.userLinearLayoutLiveData.observe(viewLifecycleOwner) {
            binding.buttonSwitchMode.isClickable = it
        }

        /*binding.buttonSwitchMode.setOnClickListener {
            dataStoreViewModel.setLinearLayoutPref(true)
            (binding.rvFoodList.layoutManager as GridLayoutManager).spanCount = if (true) 2 else 1
            foodAdapter.adapterLayoutMode =
                if (false) AdapterLayoutMode.GRID else AdapterLayoutMode.LINEAR
            setObserveData()
        }*/
        when (foodAdapter.adapterLayoutMode) {
            AdapterLayoutMode.LINEAR -> {
                dataStoreViewModel.setLinearLayoutPref(isUsingLinear = true)
                binding.buttonSwitchMode.setImageResource(R.drawable.ic_list_menu)
                (binding.rvFoodList.layoutManager as GridLayoutManager).spanCount = 2
                foodAdapter.adapterLayoutMode = AdapterLayoutMode.GRID
            }

            AdapterLayoutMode.GRID -> {
                dataStoreViewModel.setLinearLayoutPref(isUsingLinear = true)
                binding.buttonSwitchMode.setImageResource(R.drawable.ic_grid_menu)
                (binding.rvFoodList.layoutManager as GridLayoutManager).spanCount = 1
                foodAdapter.adapterLayoutMode = AdapterLayoutMode.LINEAR
            }
        }
        foodAdapter.refreshList()
        setObserveData()
    }
    //}

}