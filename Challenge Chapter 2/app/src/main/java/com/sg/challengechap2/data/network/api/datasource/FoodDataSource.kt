package com.sg.challengechap2.data.network.api.datasource

import com.sg.challengechap2.data.network.api.model.CategoryResponse
import com.sg.challengechap2.data.network.api.model.FoodResponse
import com.sg.challengechap2.data.network.api.service.RestaurantService

interface FoodDataSource {
    suspend fun getFoods(): FoodResponse
}

class FoodApiDataSource(
    private val service: RestaurantService
) : FoodDataSource{
    override suspend fun getFoods(): FoodResponse {
        TODO("Not yet implemented")
    }


}