package com.sg.challengechap2.presentation.home.adapter.category

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.sg.challengechap2.core.ViewHolderBinder
import com.sg.challengechap2.databinding.ItemListCategoryBinding
import com.sg.challengechap2.model.CategoryFood

class CategoryListAdapter(
    private val onItemClick: (CategoryFood) -> Unit
) : RecyclerView.Adapter<ViewHolder>() {

    private val differ = AsyncListDiffer(
        this,
        object : DiffUtil.ItemCallback<CategoryFood>() {
            override fun areItemsTheSame(
                oldItem: CategoryFood,
                newItem: CategoryFood
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: CategoryFood,
                newItem: CategoryFood
            ): Boolean {
                return oldItem.hashCode() == newItem.hashCode()
            }
        }
    )
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryItemViewHolder {
        val binding = ItemListCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryItemViewHolder(
            binding,
            onItemClick
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        (holder as ViewHolderBinder<CategoryFood>).bind(differ.currentList[position])
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun setData(data: List<CategoryFood>) {
        differ.submitList(data)
    }
}
