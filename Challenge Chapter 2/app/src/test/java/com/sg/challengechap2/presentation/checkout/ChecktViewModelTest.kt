package com.sg.challengechap2.presentation.checkout

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
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.rules.TestRule

class ChecktViewModelTest {

    @MockK
    lateinit var repo: CartRepository

    private lateinit var viewModel: ChecktViewModel

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
                        130000.0
                    )
                )
            )
        }
        viewModel = spyk(ChecktViewModel(repo))
        val updateResult = flow {
            emit(ResultWrapper.Success(true))
        }
        coEvery { repo.deleteAll() } returns Unit
        coEvery { repo.order(any()) } returns updateResult
    }

    @Test
    fun`test cart list`() {
        val result = viewModel.cartList.getOrAwaitValue()
        TestCase.assertEquals(result.payload?.first?.size, 3)
        TestCase.assertEquals(result.payload?.second, 130000.0)
    }

    @Test
    fun `test delete all`() {
        viewModel.deleteAll()
        coVerify { repo.deleteAll() }
    }

    @Test
    fun `test create order`() {
        runTest {
            viewModel.createOrder()
            coVerify { repo.getCartList() }
        }
    }
}
