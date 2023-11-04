package com.sg.challengechap2.presentation.splashscreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.firebase.auth.FirebaseAuth
import com.sg.challengechap2.R
import com.sg.challengechap2.data.network.firebase.auth.FirebaseAuthDataSource
import com.sg.challengechap2.data.network.firebase.auth.FirebaseAuthDataSourceImpl
import com.sg.challengechap2.data.repository.UserRepository
import com.sg.challengechap2.data.repository.UserRepositoryImpl
import com.sg.challengechap2.databinding.ActivitySplashScreenBinding
import com.sg.challengechap2.presentation.login.LoginActivity
import com.sg.challengechap2.presentation.main.MainActivity
import com.sg.challengechap2.utils.GenericViewModelFactory
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class SplashScreenActivity : AppCompatActivity() {

    private val viewModel : SplashScreenViewModel by viewModel()

    private val binding : ActivitySplashScreenBinding by lazy {
        ActivitySplashScreenBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        checkIfUserLogin()
    }

    private fun checkIfUserLogin() {
        lifecycleScope.launch {
            delay(2000)
            if (viewModel.isUserLoggedIn()){
                navigateToMain()
            } else{
                navigateToLogin()
            }
        }
    }

    private fun navigateToLogin() {
        val intent = Intent(this, LoginActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        }
        startActivity(intent)
    }

    private fun navigateToMain() {
        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        }
        startActivity(intent)
    }
}