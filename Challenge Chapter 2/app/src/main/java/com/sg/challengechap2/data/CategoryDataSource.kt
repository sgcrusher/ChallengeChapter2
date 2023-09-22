package com.sg.challengechap2.data

import com.sg.challengechap2.R
import com.sg.challengechap2.model.CategoryFood

interface CategoryDataSource{
    fun getCategories(): List<CategoryFood>
}

class CategoryDataSourceImpl() : CategoryDataSource{
    override fun getCategories(): List<CategoryFood> = mutableListOf (
        CategoryFood(R.drawable.ic_noodles, "Mie"),
        CategoryFood(R.drawable.ic_ramen, "Ramen"),
        CategoryFood(R.drawable.ic_rice, "Nasi"),
        CategoryFood(R.drawable.ic_seafood, "Makanan laut")

    )
}