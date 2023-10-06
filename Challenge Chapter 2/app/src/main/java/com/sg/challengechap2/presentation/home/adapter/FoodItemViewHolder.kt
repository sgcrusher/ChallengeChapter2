package com.sg.challengechap2.presentation.home.adapter

import androidx.recyclerview.widget.RecyclerView
import com.sg.challengechap2.R
import com.sg.challengechap2.core.ViewHolderBinder
import com.sg.challengechap2.databinding.ItemGridFoodBinding
import com.sg.challengechap2.databinding.ItemLinearFoodBinding
import com.sg.challengechap2.model.Food

class LinearFoodItemViewHolder(
    private val binding : ItemLinearFoodBinding,
    private val onItemClick : (Food) -> Unit
) : RecyclerView.ViewHolder(binding.root),ViewHolderBinder<Food>{
    override fun bind(item: Food) {
        binding.root.setOnClickListener{
            onItemClick.invoke(item)
        }
        binding.ivFoodList.setImageResource(item.foodImg)
        binding.tvFoodName.text = item.foodName
        binding.tvFoodPrice.text = itemView.context.getString(R.string.tv_food_price_format, item.foodPrice)
        binding.tvFoodShopDistance.text = itemView.context.getString(R.string.tv_shop_distance_format, item.foodShopDistance)
    }
}


class GridFoodItemViewHolder(
    private val binding : ItemGridFoodBinding,
    private val onItemClick : (Food) -> Unit
) : RecyclerView.ViewHolder(binding.root),ViewHolderBinder<Food>{
    override fun bind(item: Food) {
        binding.root.setOnClickListener{
            onItemClick.invoke(item)
        }
        binding.ivFoodGrid.setImageResource(item.foodImg)
        binding.tvFoodName.text = item.foodName
        binding.tvFoodPrice.text = itemView.context.getString(R.string.tv_food_price_format, item.foodPrice)
    }
}
