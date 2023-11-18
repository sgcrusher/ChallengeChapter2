package com.sg.challengechap2.presentation.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.sg.challengechap2.data.repository.FoodRepository
import com.sg.challengechap2.data.repository.UserRepository
import com.sg.challengechap2.model.User
import com.sg.challengechap2.tools.MainCoroutineRule
import com.sg.challengechap2.utils.ResultWrapper
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.spyk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.rules.TestRule

class FoodViewModelTest {

    @MockK
    private lateinit var foodRepo: FoodRepository

    @MockK
    private lateinit var userRepo: UserRepository

    @get:Rule
    val testRule: TestRule = InstantTaskExecutorRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val coroutineRule: TestRule = MainCoroutineRule(
        UnconfinedTestDispatcher()
    )

    private lateinit var viewModel: FoodViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        viewModel = spyk(
            FoodViewModel(foodRepo, userRepo),
            recordPrivateCalls = true
        )
        coEvery { foodRepo.getCategories() } returns flow {
            emit(
                ResultWrapper.Success(
                    listOf(
                        mockk(relaxed = true),
                        mockk(relaxed = true),
                        mockk(relaxed = true),
                        mockk(relaxed = true)
                    )
                )
            )
        }
        coEvery { foodRepo.getFoods(any()) } returns flow {
            emit(
                ResultWrapper.Success(
                    listOf(
                        mockk(relaxed = true),
                        mockk(relaxed = true),
                        mockk(relaxed = true),
                        mockk(relaxed = true)
                    )
                )
            )
        }
    }

    /*@Test
    fun `test menu live data`() {
        runTest {
            viewModel.getFoods("all")
            coVerify { foodRepo.getFoods(any()) }
            val result = viewModel.foodData.getOrAwaitValue()
            assertTrue(result is ResultWrapper.Success)
            assertTrue((result as ResultWrapper.Success<List<Food>>).payload?.size == 4)
        }
    }*/

    @Test
    fun `test getCurrentUser`() {
        runTest {
            val mockUser = mockk<User>(relaxed = true)
            every { userRepo.getCurrentUser() } returns mockUser
            val result = viewModel.getCurrentUser()
            coVerify { userRepo.getCurrentUser() }
            assertEquals(result, mockUser)
        }
    }
}
