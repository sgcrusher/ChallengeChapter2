package com.sg.challengechap2.data.repository

import com.sg.challengechap2.data.network.api.datasource.RestaurantApiDataSource
import com.sg.challengechap2.data.network.api.model.category.toCategoryList
import com.sg.challengechap2.data.network.api.model.food.toFoodList
import com.sg.challengechap2.model.CategoryFood
import com.sg.challengechap2.model.Food
import com.sg.challengechap2.utils.ResultWrapper
import com.sg.challengechap2.utils.proceedFlow
import kotlinx.coroutines.flow.Flow

interface FoodRepository {
    suspend fun getCategories(): Flow<ResultWrapper<List<CategoryFood>>>

    suspend fun getFoods(category: String? = null): Flow<ResultWrapper<List<Food>>>
}

class FoodRepositoryImpl(
    private val apiDataSource: RestaurantApiDataSource
) : FoodRepository {

    override suspend fun getCategories(): Flow<ResultWrapper<List<CategoryFood>>> {
        return proceedFlow {
            apiDataSource.getCategories().data?.toCategoryList() ?: emptyList()
        }
    }

    override suspend fun getFoods(category: String?): Flow<ResultWrapper<List<Food>>> {
        return proceedFlow {
            apiDataSource.getFoods(category).data?.toFoodList() ?: emptyList()
        }
    }
}
