package com.sg.challengechap2.presentation.register

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.sg.challengechap2.data.repository.UserRepository
import com.sg.challengechap2.tools.MainCoroutineRule
import com.sg.challengechap2.tools.getOrAwaitValue
import com.sg.challengechap2.utils.ResultWrapper
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
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

class RegisterViewModelTest {

    @MockK
    lateinit var repo: UserRepository

    private lateinit var viewModel: RegisterViewModel

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
        viewModel = spyk(RegisterViewModel(repo))
        val updateResult = flow {
            emit(ResultWrapper.Success(true))
        }
        coEvery { repo.doRegister(any(), any(), any()) } returns updateResult
    }

    @Test
    fun `test do register`() {
        viewModel.doRegister("full name", "email", "password")
        coVerify { repo.doRegister(any(), any(), any()) }
    }

    @Test
    fun `register result live data`() {
        runTest {
            viewModel.doRegister("full name", "email", "password")
            coVerify { repo.doRegister(any(), any(), any()) }
            val result = viewModel.registerResult.getOrAwaitValue()
            assertEquals(result.payload, true)
        }
    }
}
