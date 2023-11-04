package com.sg.challengechap2.data.repository

import com.sg.challengechap2.data.network.api.datasource.RestaurantApiDataSource
import com.sg.challengechap2.data.network.api.model.order.OrderRequest
import com.sg.challengechap2.data.network.api.model.order.OrdersResponse
import com.sg.challengechap2.utils.ResultWrapper
import com.sg.challengechap2.utils.proceedFlow
import kotlinx.coroutines.flow.Flow

interface OrderRepository {
    suspend fun createOrder(orderRequest: OrderRequest): Flow<ResultWrapper<OrdersResponse>>
}

class OrderRepositoryImpl(
    private val apiDataSource: RestaurantApiDataSource
) : OrderRepository {
    override suspend fun createOrder(orderRequest: OrderRequest): Flow<ResultWrapper<OrdersResponse>> {
        return proceedFlow {
            apiDataSource.createOrder(orderRequest)
        }
    }
}
