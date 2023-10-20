package com.sg.challengechap2.data.network.api.datasource

import com.sg.challengechap2.data.network.api.model.CategoryResponse
import com.sg.challengechap2.data.network.api.service.RestaurantService

interface CategoryDataSource {
    suspend fun getCategories() : CategoryResponse
}


class CategoryApiDataSource(
    private val service: RestaurantService
) : CategoryDataSource{
    override suspend fun getCategories(): CategoryResponse {
        TODO("Not yet implemented")
    }

}