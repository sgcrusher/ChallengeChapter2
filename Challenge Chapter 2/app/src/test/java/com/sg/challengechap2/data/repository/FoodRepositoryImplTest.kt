package com.sg.challengechap2.data.repository

import app.cash.turbine.test
import com.sg.challengechap2.data.network.api.datasource.RestaurantApiDataSource
import com.sg.challengechap2.data.network.api.model.category.CategoryItemResponse
import com.sg.challengechap2.data.network.api.model.category.CategoryResponse
import com.sg.challengechap2.data.network.api.model.food.FoodItemResponse
import com.sg.challengechap2.data.network.api.model.food.FoodResponse
import com.sg.challengechap2.utils.ResultWrapper
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class FoodRepositoryImplTest {
    @MockK
    lateinit var remoteDataSource: RestaurantApiDataSource

    private lateinit var menuRepository: FoodRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        menuRepository = FoodRepositoryImpl(remoteDataSource)
    }

    @Test
    fun `get categories, with result loading`() {
        val mockCategoryResponse = mockk<CategoryResponse>()
        runTest {
            coEvery { remoteDataSource.getCategories() } returns mockCategoryResponse
            menuRepository.getCategories().map {
                delay(100)
                it
            }.test {
                delay(120)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Loading)
                coVerify { remoteDataSource.getCategories() }
            }
        }
    }

    @Test
    fun `get categories, with result success`() {
        val fakeCategoryItemResponse = CategoryItemResponse(
            id = 123,
            imageUrl = "url",
            name = "name",
            slug = "slug"
        )
        val fakeCategoriesResponse = CategoryResponse(
            code = 200,
            status = true,
            message = "Success",
            data = listOf(fakeCategoryItemResponse)
        )

        runTest {
            coEvery { remoteDataSource.getCategories() } returns fakeCategoriesResponse
            menuRepository.getCategories().map {
                delay(100)
                it
            }.test {
                delay(220)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Success)
                assertEquals(data.payload?.size, 1)
                assertEquals(data.payload?.get(0)?.id, 123)
                coVerify { remoteDataSource.getCategories() }
            }
        }
    }

    @Test
    fun `get categories, with result success but empty`() {
        val fakeCategoriesResponse = CategoryResponse(
            code = 200,
            status = true,
            message = "Success but Empty",
            data = emptyList()
        )
        runTest {
            coEvery { remoteDataSource.getCategories() } returns fakeCategoriesResponse
            menuRepository.getCategories().map {
                delay(100)
                it
            }.test {
                delay(220)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Empty)
                coVerify { remoteDataSource.getCategories() }
            }
        }
    }

    @Test
    fun `get categories, with result error`() {
        runTest {
            coEvery { remoteDataSource.getCategories() } throws IllegalStateException("Mock Error")
            menuRepository.getCategories().map {
                delay(100)
                it
            }.test {
                delay(220)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Error)
                coVerify { remoteDataSource.getCategories() }
            }
        }
    }

    @Test
    fun `get foods, with result loading`() {
        val mockFoodResponse = mockk<FoodResponse>()
        runTest {
            coEvery { remoteDataSource.getFoods(any()) } returns mockFoodResponse
            menuRepository.getFoods("makanan").map {
                delay(100)
                it
            }.test {
                delay(120)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Loading)
                coVerify { remoteDataSource.getFoods(any()) }
            }
        }
    }

    @Test
    fun `get foods, with result success`() {
        val fakeMenuItemResponse = FoodItemResponse(
            id = 123,
            imageUrl = "url",
            name = "name",
            detail = "detail",
            price = 20.0,
            formattedPrice = "Rp. 20.0",
            restoAddress = "address"
        )
        val fakeMenusResponse = FoodResponse(
            code = 200,
            status = true,
            message = "Success",
            data = listOf(fakeMenuItemResponse)
        )
        runTest {
            coEvery { remoteDataSource.getFoods(any()) } returns fakeMenusResponse
            menuRepository.getFoods("makanan").map {
                delay(100)
                it
            }.test {
                delay(220)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Success)
                assertEquals(data.payload?.size, 1)
                assertEquals(data.payload?.get(0)?.foodId, 123)
                coVerify { remoteDataSource.getFoods(any()) }
            }
        }
    }

    @Test
    fun `get foods, with result success but empty`() {
        val fakeMenusResponse = FoodResponse(
            code = 200,
            status = true,
            message = "Success but Empty",
            data = emptyList()
        )
        runTest {
            coEvery { remoteDataSource.getFoods(any()) } returns fakeMenusResponse
            menuRepository.getFoods("makanan").map {
                delay(100)
                it
            }.test {
                delay(220)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Empty)
                coVerify { remoteDataSource.getFoods(any()) }
            }
        }
    }

    @Test
    fun `get foods, with result error`() {
        runTest {
            coEvery { remoteDataSource.getFoods(any()) } throws IllegalStateException("Mock Error")
            menuRepository.getFoods("makanan").map {
                delay(100)
                it
            }.test {
                delay(220)
                val data = expectMostRecentItem()
                println(data)
                assertTrue(data is ResultWrapper.Error)
                coVerify { remoteDataSource.getFoods(any()) }
            }
        }
    }
}
