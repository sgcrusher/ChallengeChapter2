package com.sg.challengechap2.presentation.profile

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.sg.challengechap2.data.repository.UserRepository
import com.sg.challengechap2.model.User
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

class ProfileViewModelTest {

    @MockK
    lateinit var repo: UserRepository

    private lateinit var viewModel: ProfileViewModel

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
        viewModel = spyk(ProfileViewModel(repo))
        val updateResult = flow {
            emit(ResultWrapper.Success(true))
        }
        coEvery { repo.getCurrentUser() } returns User("fullname", "url", "email")
        coEvery { repo.sendChangePasswordRequestByEmail() } returns true
        coEvery { repo.doLogout() } returns true
        coEvery { repo.updateProfile(any(), any()) } returns updateResult
    }

    @Test
    fun`get current user`() {
        viewModel.getCurrentUser()
        coVerify { repo.getCurrentUser() }
    }

    @Test
    fun`create change password request`() {
        viewModel.createChangePwdRequest()
        coVerify { repo.sendChangePasswordRequestByEmail() }
    }

    @Test
    fun`do logout`() {
        viewModel.doLogout()
        coVerify { repo.doLogout() }
    }
    /*@Test
    fun`update photo profile`() {
        viewModel.updateProfilePicture()
        coVerify { repo.updateProfile(any()) }
    }*/

    @Test
    fun`update profile`() {
        viewModel.updateFullName("full name")
        coVerify { repo.updateProfile(any()) }
    }

    @Test
    fun`test update profile result live data`() {
        runTest {
            viewModel.updateFullName("fullname")
            coVerify { repo.updateProfile(any()) }
            val result = viewModel.changeProfileResult.getOrAwaitValue()
            assert(result is ResultWrapper.Success)
        }
    }
}
