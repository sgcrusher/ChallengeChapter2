package com.sg.challengechap2.presentation.cart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.sg.challengechap2.R
import com.sg.challengechap2.core.ViewHolderBinder
import com.sg.challengechap2.data.CategoryDataSource
import com.sg.challengechap2.data.FoodDataSource
import com.sg.challengechap2.databinding.ItemListCartBinding
import com.sg.challengechap2.databinding.ItemListCategoryBinding
import com.sg.challengechap2.model.CategoryFood
import com.sg.challengechap2.model.Food
import com.sg.challengechap2.presentation.home.adapter.CategoryItemViewHolder


class CartListAdapter (
    private val FoodDataSource: FoodDataSource,
) : RecyclerView.Adapter<CartListViewHolder>(){

    private val dataDiffer = AsyncListDiffer(this,object : DiffUtil.ItemCallback<Food>(){
        override fun areItemsTheSame(oldItem: Food, newItem: Food): Boolean {
            return oldItem.foodId == newItem.foodId
        }

        override fun areContentsTheSame(oldItem: Food, newItem: Food): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    })


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemListCartBinding.inflate(inflater, parent, false)
        return CartListViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return dataDiffer.currentList.size
    }

    override fun onBindViewHolder(holder: CartListViewHolder, position: Int) {
        (holder as ViewHolderBinder<Food>).bind(dataDiffer.currentList[position])
    }

    fun setData(foodData:List<Food>){
        dataDiffer.submitList(foodData)
    }


}




class CartListViewHolder(
    private val binding: ItemListCartBinding,

    ) : RecyclerView.ViewHolder(binding.root), ViewHolderBinder<Food> {
    override fun bind(item: Food) {

        binding.tvFoodCartName.text = item.foodName
        binding.tvFoodCartPrice.text = itemView.context.getString(R.string.tv_food_price_format, item.foodPrice)
        binding.ivCartMenu.setImageResource(item.foodImg)
    }
}