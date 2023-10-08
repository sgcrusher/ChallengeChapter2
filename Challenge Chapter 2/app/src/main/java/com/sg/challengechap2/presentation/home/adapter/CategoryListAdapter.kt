package com.sg.challengechap2.presentation.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sg.challengechap2.core.ViewHolderBinder
import com.sg.challengechap2.data.dummy.CategoryDataSource
import com.sg.challengechap2.databinding.ItemListCategoryBinding
import com.sg.challengechap2.model.CategoryFood

class CategoryListAdapter (
    private val categoryDataSource: CategoryDataSource,
    private val onItemClick: (CategoryFood) -> Unit
) : RecyclerView.Adapter<CategoryItemViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemListCategoryBinding.inflate(inflater, parent, false)
        return CategoryItemViewHolder(binding, onItemClick)
    }

    override fun getItemCount(): Int {
        return categoryDataSource.getCategories().size
    }

    override fun onBindViewHolder(holder: CategoryItemViewHolder, position: Int) {
        val category = categoryDataSource.getCategories()[position]
        holder.bind(category)
    }

}



class CategoryItemViewHolder(
    private val binding: ItemListCategoryBinding,
    private val onItemClick: (CategoryFood) -> Unit
) : RecyclerView.ViewHolder(binding.root), ViewHolderBinder<CategoryFood> {
    override fun bind(item: CategoryFood) {
        binding.root.setOnClickListener{
            onItemClick.invoke(item)
        }
        binding.icCategoryImg.setImageResource(item.categoryImg)
        binding.tvCategory.text = item.categoryName
    }
}