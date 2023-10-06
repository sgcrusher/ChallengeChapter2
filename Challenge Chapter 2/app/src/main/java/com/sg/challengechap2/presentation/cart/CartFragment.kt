package com.sg.challengechap2.presentation.cart

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.sg.challengechap2.R
import com.sg.challengechap2.data.CategoryDataSource
import com.sg.challengechap2.data.CategoryDataSourceImpl
import com.sg.challengechap2.data.FoodDataSource
import com.sg.challengechap2.data.FoodDataSourceImpl
import com.sg.challengechap2.databinding.FragmentCartBinding
import com.sg.challengechap2.databinding.FragmentFoodListBinding


class CartFragment : Fragment() {
    private lateinit var binding: FragmentCartBinding

    private val FoodDataSource: FoodDataSource by lazy {
        FoodDataSourceImpl()
    }
    private val adapter : CartListAdapter by lazy {
        CartListAdapter(FoodDataSource)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCartBinding.inflate(inflater,container,false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupCartRecyclerview()
    }

    private fun setupCartRecyclerview() {
        binding.rvCartList.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = this@CartFragment.adapter
        }
        adapter.setData(FoodDataSource.getFoodData())

    }
}