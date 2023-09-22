package com.sg.challengechap2.presentation.detailfragmentpage

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.sg.challengechap2.R
import com.sg.challengechap2.databinding.FragmentDetailBinding
import com.sg.challengechap2.model.Food


class DetailFragment : Fragment() {

    private lateinit var binding: FragmentDetailBinding

    private val food: Food? by lazy {
        DetailFragmentArgs.fromBundle(arguments as Bundle).food
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showFoodDetail()
        setClickListener()
        countFoodTotal()
    }

    private fun setClickListener() {
        binding.tvShopLocation.setOnClickListener{
            navigateToMap()
        }
        binding.buttonAddToChart.setOnClickListener{
            addToChart()
        }
    }

    private fun addToChart() {
        Toast.makeText(requireContext(), "${food?.foodName} Success to add.", Toast.LENGTH_SHORT).show()
    }

    private fun navigateToMap() {
        val url = food?.foodUrllocation
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }

    private fun countFoodTotal(){
        var foodTotal = 1
        binding.ivPlusButton.setOnClickListener{
            foodTotal += 1
            binding.tvFoodTotal.text = foodTotal.toString()
        }
        binding.ivMinusButton.setOnClickListener{
            foodTotal -= 1
            binding.tvFoodTotal.text = foodTotal.toString()
        }
    }

    private fun showFoodDetail() {
        if (food != null) {
            binding.ivFoodDetailImg.setImageResource(food?.foodImg!!)
            binding.tvFoodNameDetail.text = food?.foodName
            binding.tvFoodPrice.text = getString(R.string.tv_food_price_format, food?.foodPrice)
            binding.tvShopLocation.text = food?.foodShopLocation
            binding.tvFoodDescription.text = food?.foodDescription
            binding.tvFoodShopDistance.text =
                getString(R.string.tv_shop_distance_format, food?.foodShopDistance)

        }
    }
}