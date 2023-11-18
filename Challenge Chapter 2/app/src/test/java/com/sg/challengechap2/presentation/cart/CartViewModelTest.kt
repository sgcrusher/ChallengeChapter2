package com.sg.challengechap2.presentation.cart

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.sg.challengechap2.data.repository.CartRepository
import com.sg.challengechap2.tools.MainCoroutineRule
import com.sg.challengechap2.tools.getOrAwaitValue
import com.sg.challengechap2.utils.ResultWrapper
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.spyk
import junit.framework.TestCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.rules.TestRule

class CartViewModelTest {

    @MockK
    lateinit var repo: CartRepository

    private lateinit var viewModel: CartViewModel

    @get:Rule
    val testRule: TestRule = InstantTaskExecutorRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val coroutineRule: TestRule = MainCoroutineRule(
        UnconfinedTestDispatcher()
    )

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        coEvery { repo.getCartList() } returns flow {
            emit(
                ResultWrapper.Success(
                    Pair(
                        listOf(
                            mockk(relaxed = true),
                            mockk(relaxed = true),
                            mockk(relaxed = true)
                        ),
                        127000.0
                    )
                )
            )
        }
        viewModel = spyk(CartViewModel(repo))
        val updateResult = flow {
            emit(ResultWrapper.Success(true))
        }
        coEvery { repo.increaseCart(any()) } returns updateResult
        coEvery { repo.decreaseCart(any()) } returns updateResult
        coEvery { repo.deleteCart(any()) } returns updateResult
        coEvery { repo.setCartNotes(any()) } returns updateResult
    }

    @Test
    fun`cart list`() {
        val result = viewModel.cartList.getOrAwaitValue()
        TestCase.assertEquals(result.payload?.first?.size, 3)
        TestCase.assertEquals(result.payload?.second, 127000.0)
    }

    @Test
    fun `decrease cart`() {
        viewModel.decreaseCart(mockk())
        coVerify { repo.decreaseCart(any()) }
    }

    @Test
    fun `increase cart`() {
        viewModel.increaseCart(mockk())
        coVerify { repo.increaseCart(any()) }
    }

    @Test
    fun `delete cart`() {
        viewModel.deleteCart(mockk())
        coVerify { repo.deleteCart(any()) }
    }

    @Test
    fun `set cart notes cart`() {
        viewModel.updateNotes(mockk())
        coVerify { repo.setCartNotes(any()) }
    }
}
