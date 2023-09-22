package com.sg.challengechap2.presentation.homefragmentpage

import android.icu.util.ULocale
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.sg.challengechap2.R
import com.sg.challengechap2.data.CategoryDataSource
import com.sg.challengechap2.data.CategoryDataSourceImpl
import com.sg.challengechap2.databinding.FragmentFoodListBinding
import com.sg.challengechap2.model.CategoryFood
import com.sg.challengechap2.presentation.homefragmentpage.adapter.CategoryListAdapter
import java.util.Locale.Category


class FoodListFragment : Fragment() {

    private lateinit var binding: FragmentFoodListBinding

    private val categoryDataSource: CategoryDataSource by lazy {
        CategoryDataSourceImpl()
    }


    private val categoryAdapter: CategoryListAdapter by lazy {
        CategoryListAdapter(categoryDataSource){
            clickCategory(it)
        }
    }

    private fun clickCategory(category : CategoryFood) {
        val categoryName = category.categoryName
        Toast.makeText(requireContext(), "$categoryName", Toast.LENGTH_SHORT).show()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFoodListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupCategoryRecyclerview()
    }

    private fun setupCategoryRecyclerview() {
        binding.rvCategoryList.apply {
            layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
            adapter = this@FoodListFragment.categoryAdapter
        }
    }

}