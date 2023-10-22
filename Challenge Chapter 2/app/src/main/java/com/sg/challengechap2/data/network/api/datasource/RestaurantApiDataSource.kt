package com.sg.challengechap2.data.network.api.datasource


import com.sg.challengechap2.data.network.api.model.category.CategoryResponse
import com.sg.challengechap2.data.network.api.model.food.FoodResponse
import com.sg.challengechap2.data.network.api.model.order.OrderRequest
import com.sg.challengechap2.data.network.api.model.order.OrdersResponse
import com.sg.challengechap2.data.network.api.service.RestaurantService

interface RestaurantApiDataSource{

    suspend fun getFoods(category: String? = null): FoodResponse

    suspend fun getCategories(): CategoryResponse

    suspend fun createOrder(orderRequest: OrderRequest): OrdersResponse
}

class RestaurantApiDataSourceImpl(
    private val service: RestaurantService
) : RestaurantApiDataSource{
    override suspend fun getFoods(category: String?): FoodResponse {
        return service.getFoods(category)
    }

    override suspend fun getCategories(): CategoryResponse {
        return service.getCategories()
    }

    override suspend fun createOrder(orderRequest: OrderRequest): OrdersResponse {
        return service.createOrder(orderRequest)
    }


}