package com.sg.challengechap2.presentation.login

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.sg.challengechap2.R
import com.sg.challengechap2.databinding.ActivityLoginBinding
import com.sg.challengechap2.presentation.main.MainActivity
import com.sg.challengechap2.presentation.register.RegisterActivity
import com.sg.challengechap2.utils.highLightWord
import com.sg.challengechap2.utils.proceedWhen
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginActivity : AppCompatActivity() {

    private val binding: ActivityLoginBinding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }

    private val viewModel: LoginViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupForm()
        setClickListeners()
        observeResult()
    }

    private fun setupForm() {
        // todo : setup form
        binding.layoutForm.tiEmail.isVisible = true
        binding.layoutForm.tilPassword.isVisible = true
    }

    private fun observeResult() {
        viewModel.loginResult.observe(this) {
            it.proceedWhen(
                doOnSuccess = {
                    binding.pbLoading.isVisible = false
                    binding.btnLogin.isVisible = true
                    navigateToMain()
                },
                doOnError = {
                    binding.pbLoading.isVisible = false
                    binding.btnLogin.isVisible = true
                    Toast.makeText(
                        this,
                        "Login Failed : ${it.exception?.message.orEmpty()}",
                        Toast.LENGTH_SHORT
                    ).show()
                },
                doOnLoading = {
                    binding.pbLoading.isVisible = true
                    binding.btnLogin.isVisible = false
                }

            )
        }
    }

    private fun navigateToMain() {
        // todo : navigate to main
        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        }
        startActivity(intent)
    }

    private fun setClickListeners() {
        // todo :set click listener
        binding.tvNavToRegister.highLightWord(getString(R.string.text_register)) {
            navigateToRegister()
        }

        binding.btnLogin.setOnClickListener {
            doLogin()
        }
    }

    private fun navigateToRegister() {
        startActivity(
            Intent(this, RegisterActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP
            }
        )
    }

    private fun doLogin() {
        // todo : do login process
        if (isFormValid()) {
            val email = binding.layoutForm.etEmail.text.toString().trim()
            val password = binding.layoutForm.etPassword.text.toString().trim()
            viewModel.doLogin(email, password)
        }
    }

    private fun isFormValid(): Boolean {
        // todo : create result from email validation and password
        val email = binding.layoutForm.etEmail.text.toString().trim()
        val password = binding.layoutForm.etPassword.text.toString().trim()
        return checkEmailValidation(email) && checkPasswordValidation(password)
    }

    private fun checkEmailValidation(email: String): Boolean {
        // todo : check email is valid
        return if (email.isEmpty()) {
            // email cannot be empty
            binding.layoutForm.tiEmail.isErrorEnabled = true
            binding.layoutForm.tiEmail.error = getString(R.string.text_error_email_empty)
            false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            // email format is correct
            binding.layoutForm.tiEmail.isErrorEnabled = true
            binding.layoutForm.tiEmail.error = getString(R.string.text_error_email_invalid)
            false
        } else {
            binding.layoutForm.tiEmail.isErrorEnabled = false
            true
        }
    }

    private fun checkPasswordValidation(
        password: String
    ): Boolean {
        // todo : check password is valid
        return if (password.isEmpty()) {
            binding.layoutForm.tilPassword.isErrorEnabled = true
            binding.layoutForm.tilPassword.error = getString(R.string.text_error_password_empty)
            false
        } else if (password.length < 8) {
            binding.layoutForm.tilPassword.isErrorEnabled = true
            binding.layoutForm.tilPassword.error = getString(R.string.text_error_password_less_than_8_char)
            false
        } else {
            binding.layoutForm.tilPassword.isErrorEnabled = false
            true
        }
    }
}
