package com.sg.challengechap2.presentation.splashscreen

import androidx.lifecycle.ViewModel
import com.sg.challengechap2.data.repository.UserRepository

class SplashScreenViewModel(private val repo: UserRepository) : ViewModel(){
    fun isUserLoggedIn() = repo.isLoggedIn()
}