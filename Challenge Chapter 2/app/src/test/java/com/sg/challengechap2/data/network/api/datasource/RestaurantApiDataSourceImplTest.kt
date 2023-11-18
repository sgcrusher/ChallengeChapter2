package com.sg.challengechap2.data.network.api.datasource

import com.sg.challengechap2.data.network.api.model.category.CategoryResponse
import com.sg.challengechap2.data.network.api.model.food.FoodResponse
import com.sg.challengechap2.data.network.api.model.order.OrderRequest
import com.sg.challengechap2.data.network.api.model.order.OrdersResponse
import com.sg.challengechap2.data.network.api.service.RestaurantService
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions.*

class RestaurantApiDataSourceImplTest {

    @MockK
    lateinit var service: RestaurantService

    private lateinit var dataSource: RestaurantApiDataSourceImpl

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        dataSource = RestaurantApiDataSourceImpl(service)
    }

    @Test
    fun getMenus() {
        runTest {
            val mockResponse = mockk<FoodResponse>(relaxed = true)
            coEvery { service.getFoods(any()) } returns mockResponse
            val response = dataSource.getFoods("makanan")
            coVerify { service.getFoods(any()) }
            assertEquals(response, mockResponse)
        }
    }

    @Test
    fun getCategories() {
        runTest {
            val mockResponse = mockk<CategoryResponse>(relaxed = true)
            coEvery { service.getCategories() } returns mockResponse
            val response = dataSource.getCategories()
            coVerify { service.getCategories() }
            assertEquals(response, mockResponse)
        }
    }

    @Test
    fun createOrder() {
        runTest {
            val mockResponse = mockk<OrdersResponse>(relaxed = true)
            val mockRequest = mockk<OrderRequest>(relaxed = true)
            coEvery { service.createOrder(any()) } returns mockResponse
            val response = dataSource.createOrder(mockRequest)
            coVerify { service.createOrder(any()) }
            assertEquals(response, mockResponse)
        }
    }
}
