package com.sg.challengechap2.presentation.home.adapter.category

import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.sg.challengechap2.core.ViewHolderBinder
import com.sg.challengechap2.databinding.ItemListCategoryBinding
import com.sg.challengechap2.model.CategoryFood

class CategoryItemViewHolder(
    private val binding: ItemListCategoryBinding,
    private val onItemClick: (CategoryFood) -> Unit
) : RecyclerView.ViewHolder(binding.root), ViewHolderBinder<CategoryFood> {
    override fun bind(item: CategoryFood) {
        binding.root.setOnClickListener{
            onItemClick.invoke(item)
        }
        binding.icCategoryImg.load(item.categoryImg)
        binding.tvCategory.text = item.categoryName
    }
}