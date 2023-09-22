package com.sg.challengechap2.presentation.homefragmentpage.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.sg.challengechap2.core.ViewHolderBinder
import com.sg.challengechap2.data.CategoryDataSource
import com.sg.challengechap2.databinding.ItemGridFoodBinding
import com.sg.challengechap2.databinding.ItemLinearFoodBinding
import com.sg.challengechap2.model.Food

class FoodListAdapter (
    var adapterLayoutMode: AdapterLayoutMode,
    private val onItemClick : (Food) -> Unit
) : RecyclerView.Adapter<ViewHolder>(){

    private val dataDiffer = AsyncListDiffer(this,object : DiffUtil.ItemCallback<Food>(){
        override fun areItemsTheSame(oldItem: Food, newItem: Food): Boolean {
            return oldItem.foodId == newItem.foodId
        }

        override fun areContentsTheSame(oldItem: Food, newItem: Food): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }

    })


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return when(viewType){
            AdapterLayoutMode.LINEAR.ordinal -> {
                LinearFoodItemViewHolder(
                    binding = ItemLinearFoodBinding.inflate(
                        LayoutInflater.from(parent.context), parent,false
                    ),
                    onItemClick = onItemClick
                )
            }
            else -> {
                GridFoodItemViewHolder(
                    binding = ItemGridFoodBinding.inflate(
                        LayoutInflater.from(parent.context), parent, false
                    ),
                    onItemClick = onItemClick
                )
            }
        }
    }

    override fun getItemCount(): Int {
        return dataDiffer.currentList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        (holder as ViewHolderBinder<Food>).bind(dataDiffer.currentList[position])
    }

    override fun getItemViewType(position: Int): Int {
        return adapterLayoutMode.ordinal
    }

    fun submitList(data : List<Food>){
        dataDiffer.submitList(data)
    }
    fun refreshList(){
        notifyItemRangeChanged(0,dataDiffer.currentList.size)
    }

}
