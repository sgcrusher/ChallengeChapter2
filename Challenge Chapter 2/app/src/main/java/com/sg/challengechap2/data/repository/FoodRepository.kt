package com.sg.challengechap2.data.repository

import com.sg.challengechap2.data.dummy.CategoryDataSource
import com.sg.challengechap2.data.local.database.datasource.FoodDataSource
import com.sg.challengechap2.data.local.database.mapper.toFoodList
import com.sg.challengechap2.model.CategoryFood
import com.sg.challengechap2.model.Food
import com.sg.challengechap2.presentation.utils.ResultWrapper
import com.sg.challengechap2.presentation.utils.proceed
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

interface FoodRepository {
   // fun getCategories(): List<CategoryFood>

    fun getFoods(): Flow<ResultWrapper<List<Food>>>
}

class FoodRepositoryImpl(
    private val foodDataSource: FoodDataSource,
   // private val categoryDataSource: CategoryDataSource
) : FoodRepository {
   // override fun getCategories(): List<CategoryFood> {
    //    return categoryDataSource.getCategories()
   // }

    override fun getFoods(): Flow<ResultWrapper<List<Food>>> {
        return foodDataSource.getAllFoods().map { proceed { it.toFoodList() } }.onStart {
            emit((ResultWrapper.Loading()))
            delay(2000)
        }
    }

}