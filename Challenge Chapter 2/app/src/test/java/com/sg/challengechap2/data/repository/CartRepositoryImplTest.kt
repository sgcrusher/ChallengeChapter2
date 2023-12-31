package com.sg.challengechap2.data.repository

import app.cash.turbine.test
import com.sg.challengechap2.data.local.database.datasource.CartDataSource
import com.sg.challengechap2.data.local.database.entity.CartEntity
import com.sg.challengechap2.data.network.api.datasource.RestaurantApiDataSource
import com.sg.challengechap2.data.network.api.model.order.OrdersResponse
import com.sg.challengechap2.model.Cart
import com.sg.challengechap2.model.Food
import com.sg.challengechap2.utils.ResultWrapper
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class CartRepositoryImplTest {
    @MockK
    lateinit var localDataSource: CartDataSource

    @MockK
    lateinit var remoteDataSource: RestaurantApiDataSource

    private lateinit var repository: CartRepository

    private val fakeCartList = listOf(
        CartEntity(
            cartId = 1,
            foodId = 1,
            foodName = "Nasi Goreng",
            foodPrice = 15000.0,
            foodImgUrl = "url",
            itemQuantity = 2,
            itemNotes = "notes"
        ),
        CartEntity(
            cartId = 2,
            foodId = 1,
            foodName = "Nasi Ayam",
            foodPrice = 20000.0,
            foodImgUrl = "url",
            itemQuantity = 2,
            itemNotes = "notes"
        )
    )
    val mockCart = Cart(
        id = 1,
        foodId = 1,
        foodName = "Nasi Goreng",
        foodPrice = 15000.0,
        foodImgUrl = "url",
        itemQuantity = 2,
        itemNotes = "notes"
    )

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        repository = CartRepositoryImpl(localDataSource, remoteDataSource)
    }

    @Test
    fun deleteAll() {
        runTest {
            coEvery { localDataSource.deleteAllCarts() } returns Unit
            val result = repository.deleteAll()
            coVerify { localDataSource.deleteAllCarts() }
            assertEquals(result, Unit)
        }
    }

    @Test
    fun `get user cart data, result success`() {
        coEvery { localDataSource.getAllCarts() } returns flow {
            emit(fakeCartList)
        }
        runTest {
            repository.getCartList().map {
                delay(100)
                it
            }.test {
                delay(2220)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Success)
                assertEquals(data.payload?.first?.size, 2)
                assertEquals(data.payload?.second, 70000.0)
                coVerify { localDataSource.getAllCarts() }
            }
        }
    }

    @Test
    fun `get user cart data, result loading`() {
        coEvery { localDataSource.getAllCarts() } returns flow {
            emit(fakeCartList)
        }
        runTest {
            repository.getCartList().map {
                delay(100)
                it
            }.test {
                delay(2120)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Loading)
                coVerify { localDataSource.getAllCarts() }
            }
        }
    }

    @Test
    fun `get user cart data, result empty`() {
        coEvery { localDataSource.getAllCarts() } returns flow {
            emit(emptyList())
        }
        runTest {
            repository.getCartList().map {
                delay(100)
                it
            }.test {
                delay(2220)
                val data = expectMostRecentItem()
                println(data)
                assertTrue(data is ResultWrapper.Empty)
                coVerify { localDataSource.getAllCarts() }
            }
        }
    }

    @Test
    fun `get user cart data, result error`() {
        coEvery { localDataSource.getAllCarts() } returns flow {
            throw IllegalStateException("Mock Error")
        }
        runTest {
            repository.getCartList().map {
                delay(100)
                it
            }.test {
                delay(2220)
                val data = expectMostRecentItem()
                println(data)
                assertTrue(data is ResultWrapper.Error)
                coVerify { localDataSource.getAllCarts() }
            }
        }
    }

    @Test
    fun `create cart product id not null, result success`() {
        runTest {
            val mockFood = mockk<Food>(relaxed = true)
            coEvery { localDataSource.insertCart(any()) } returns 1
            repository.createCart(mockFood, 2).map {
                delay(100)
                it
            }.test {
                delay(220)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Success)
                assertEquals(data.payload, true)
                coVerify { localDataSource.insertCart(any()) }
            }
        }
    }

    @Test
    fun `create cart product id not null, result loading`() {
        runTest {
            val mockFood = mockk<Food>(relaxed = true)
            coEvery { localDataSource.insertCart(any()) } returns 1
            repository.createCart(mockFood, 2).map {
                delay(100)
                it
            }.test {
                delay(120)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Loading)
                coVerify { localDataSource.insertCart(any()) }
            }
        }
    }

    @Test
    fun `create cart product id not null, result error`() {
        runTest {
            val mockFood = mockk<Food>(relaxed = true)
            coEvery { localDataSource.insertCart(any()) } throws IllegalStateException("Mock Error")
            repository.createCart(mockFood, 1).map {
                delay(100)
                it
            }.test {
                delay(210)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Error)
                coVerify { localDataSource.insertCart(any()) }
            }
        }
    }

    @Test
    fun `create cart product id null, result error`() {
        runTest {
            val mockFood = mockk<Food>(relaxed = true) {
                every { foodId } returns null
            }
            coEvery { localDataSource.insertCart(any()) } returns 1
            repository.createCart(mockFood, 2).map {
                delay(100)
                it
            }.test {
                delay(220)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Error)
                assertEquals(data.exception?.message, "Product ID not found")
                coVerify(atLeast = 0) { localDataSource.insertCart(any()) }
            }
        }
    }

    @Test
    fun `delete cart, result success`() {
        runTest {
            val mockCart = mockk<Cart>(relaxed = true)
            coEvery { localDataSource.deleteCart(any()) } returns 1
            repository.deleteCart(mockCart).map {
                delay(100)
                it
            }.test {
                delay(220)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Success)
                coVerify { localDataSource.deleteCart(any()) }
            }
        }
    }

    @Test
    fun `delete cart, result loading`() {
        runTest {
            val mockCart = mockk<Cart>(relaxed = true)
            coEvery { localDataSource.deleteCart(any()) } returns 1
            repository.deleteCart(mockCart).map {
                delay(100)
                it
            }.test {
                delay(120)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Loading)
                coVerify { localDataSource.deleteCart(any()) }
            }
        }
    }

    @Test
    fun `delete cart, result error`() {
        runTest {
            val mockCart = mockk<Cart>(relaxed = true)
            coEvery { localDataSource.deleteCart(any()) } throws IllegalStateException("Mock Error")
            repository.deleteCart(mockCart).map {
                delay(100)
                it
            }.test {
                delay(220)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Error)
                coVerify { localDataSource.deleteCart(any()) }
            }
        }
    }

    @Test
    fun `increase cart, result success`() {
        runTest {
            val mockCart = mockk<Cart>(relaxed = true)
            coEvery { localDataSource.updateCart(any()) } returns 1
            repository.increaseCart(mockCart).map {
                delay(100)
                it
            }.test {
                delay(220)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Success)
                coVerify { localDataSource.updateCart(any()) }
            }
        }
    }

    @Test
    fun `decrease cart when quantity less than equal 0`() {
        runTest {
            coEvery { localDataSource.deleteCart(any()) } returns 1
            coEvery { localDataSource.updateCart(any()) } returns 1
            repository.decreaseCart(mockCart).map {
                delay(100)
                it
            }.test {
                delay(220)
                val data = expectMostRecentItem()
                assertEquals(data.payload, true)
                coVerify(atLeast = 0) { localDataSource.deleteCart(any()) }
                coVerify(atLeast = 1) { localDataSource.updateCart(any()) }
            }
        }
    }

    @Test
    fun `decrease cart when quantity more than equal 0`() {
        runTest {
            coEvery { localDataSource.deleteCart(any()) } returns 1
            coEvery { localDataSource.updateCart(any()) } returns 1
            repository.decreaseCart(mockCart).map {
                delay(100)
                it
            }.test {
                delay(220)
                val data = expectMostRecentItem()
                assertEquals(data.payload, true)
                coVerify(atLeast = 0) { localDataSource.deleteCart(any()) }
                coVerify(atLeast = 1) { localDataSource.updateCart(any()) }
            }
        }
    }

    @Test
    fun `set cart notes, result success`() {
        runTest {
            val mockCart = mockk<Cart>(relaxed = true)
            coEvery { localDataSource.updateCart(any()) } returns 1
            repository.setCartNotes(mockCart).map {
                delay(100)
                it
            }.test {
                delay(220)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Success)
                coVerify { localDataSource.updateCart(any()) }
            }
        }
    }

    @Test
    fun `post order to api, result success`() {
        runTest {
            val mockCart1 = mockk<Cart>(relaxed = true)
            val mockCart2 = mockk<Cart>(relaxed = true)
            val listCart = listOf(mockCart1, mockCart2)
            coEvery { remoteDataSource.createOrder(any()) } returns OrdersResponse(
                code = 200,
                message = "Success",
                status = true
            )
            repository.order(listCart).map {
                delay(100)
                it
            }.test {
                delay(220)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Success)
                coVerify { remoteDataSource.createOrder(any()) }
            }
        }
    }

    @Test
    fun `post order to api, result loading`() {
        runTest {
            val mockCart1 = mockk<Cart>(relaxed = true)
            val mockCart2 = mockk<Cart>(relaxed = true)
            val listCart = listOf(mockCart1, mockCart2)
            coEvery { remoteDataSource.createOrder(any()) } returns OrdersResponse(
                code = 200,
                message = "Success",
                status = true
            )
            repository.order(listCart).map {
                delay(100)
                it
            }.test {
                delay(120)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Loading)
                coVerify { remoteDataSource.createOrder(any()) }
            }
        }
    }

    @Test
    fun `post order to api, result error`() {
        runTest {
            val mockCart1 = mockk<Cart>(relaxed = true)
            val mockCart2 = mockk<Cart>(relaxed = true)
            val listCart = listOf(mockCart1, mockCart2)
            coEvery { remoteDataSource.createOrder(any()) } throws IllegalStateException("Mock Error")
            repository.order(listCart).map {
                delay(100)
                it
            }.test {
                delay(220)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Error)
                coVerify { remoteDataSource.createOrder(any()) }
            }
        }
    }
}
