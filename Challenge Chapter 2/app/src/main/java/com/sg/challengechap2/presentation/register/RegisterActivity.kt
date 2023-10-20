package com.sg.challengechap2.presentation.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.isVisible
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.sg.challengechap2.R
import com.sg.challengechap2.utils.highLightWord
import com.sg.challengechap2.data.network.firebase.auth.FirebaseAuthDataSource
import com.sg.challengechap2.data.network.firebase.auth.FirebaseAuthDataSourceImpl
import com.sg.challengechap2.data.repository.UserRepository
import com.sg.challengechap2.data.repository.UserRepositoryImpl
import com.sg.challengechap2.databinding.ActivityRegisterBinding
import com.sg.challengechap2.presentation.login.LoginActivity
import com.sg.challengechap2.presentation.main.MainActivity
import com.sg.challengechap2.utils.GenericViewModelFactory
import com.sg.challengechap2.utils.proceedWhen

class RegisterActivity : AppCompatActivity() {

    private val binding : ActivityRegisterBinding by lazy {
        ActivityRegisterBinding.inflate(layoutInflater)
    }

private fun createViewModel(): RegisterViewModel {
    val firebaseAuth = FirebaseAuth.getInstance()
    val dataSource: FirebaseAuthDataSource = FirebaseAuthDataSourceImpl(firebaseAuth)
    val repository: UserRepository = UserRepositoryImpl(dataSource)
    return RegisterViewModel(repository)
}

    private val viewModel: RegisterViewModel by viewModels {
        GenericViewModelFactory.create(createViewModel())
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
    }

    private fun setClickListeners() {
        //todo : setup click listener
        binding.tvNavToLogin.highLightWord(getString(
            R.string.txt_login)){
            navigateToLogin()
        }

        //todo : setup btn register
        binding.btnRegister.setOnClickListener {
            doRegister()
        }
    }

    private fun navigateToLogin() {
        //todo : navigate to login
        val intent = Intent(this, LoginActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP
        }
        startActivity(intent)
    }

    private fun doRegister() {
        //todo : check form first, if pass then register
        if(isFormValid()){
            val name = binding.layoutForm.etName.text.toString().trim()
            val email = binding.layoutForm.etEmail.text.toString().trim()
            val password = binding.layoutForm.etPassword.text.toString().trim()
            val confirmPassword = binding.layoutForm.etConfirmPassword.text.toString().trim()
            viewModel.doRegister(name, email, password)
        }
    }

    private fun observeResult() {
        viewModel.registerResult.observe(this){
            it.proceedWhen(
                doOnSuccess = {
                    binding.pbLoading.isVisible = false
                    binding.btnRegister.isVisible = true
                    binding.btnRegister.isEnabled = false
                    navigateToMain()
                },
                doOnLoading = {
                    binding.pbLoading.isVisible = true
                    binding.btnRegister.isVisible = false
                },
                doOnError = {
                    binding.pbLoading.isVisible = false
                    binding.btnRegister.isVisible = true
                    binding.btnRegister.isEnabled = true
                    Toast.makeText(
                        this,
                        "Register Failed: ${it.exception?.message.orEmpty()}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            )
        }
    }

    private fun navigateToMain() {
        //todo : navigate to Main
        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        }
        startActivity(intent)
    }


    private fun setupForm() {
        //todo : setup form
        binding.layoutForm.tiName.isVisible = true
        binding.layoutForm.tiEmail.isVisible = true
        binding.layoutForm.tilPassword.isVisible = true
        binding.layoutForm.tilConfirmPassword.isVisible = true
    }

    private fun isFormValid(): Boolean {
        //todo : check form validation
        val name = binding.layoutForm.etName.text.toString().trim()
        val email = binding.layoutForm.etEmail.text.toString().trim()
        val password = binding.layoutForm.etPassword.text.toString().trim()
        val confirmPassword = binding.layoutForm.etConfirmPassword.text.toString().trim()

        return checkNameValidation(name) && checkEmailValidation(email)
                && checkPasswordValidation(password, binding.layoutForm.tilPassword)
                && checkPasswordValidation(confirmPassword, binding.layoutForm.tilConfirmPassword)
                && checkPwdAndConfirmPwd(password, confirmPassword)
    }

    private fun checkNameValidation(fullName: String): Boolean {
        //todo : check form validation
        return if (fullName.isEmpty()){
            binding.layoutForm.tiName.isErrorEnabled = true
            binding.layoutForm.tiName.error = getString(R.string.text_error_name_cannot_empty)
            false
        } else{
            binding.layoutForm.tiName.isErrorEnabled = false
            true
        }
    }

    private fun checkEmailValidation(email: String): Boolean {
        //todo : check form validation
        return if (email.isEmpty()){
            //email cannot be empty
            binding.layoutForm.tiEmail.isErrorEnabled = true
            binding.layoutForm.tiEmail.error = getString(R.string.text_error_email_empty)
            false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            //email format is correct
            binding.layoutForm.tiEmail.isErrorEnabled = true
            binding.layoutForm.tiEmail.error = getString(R.string.text_error_email_invalid)
            false
        } else{
            binding.layoutForm.tiEmail.isErrorEnabled = false
            true
        }
    }

    private fun checkPasswordValidation(
        password: String,
        textInputLayout: TextInputLayout
    ): Boolean {
        //todo : check form validation
        return if (password.isEmpty()){
            textInputLayout.isErrorEnabled = true
            textInputLayout.error = getString(R.string.text_error_password_empty)
            false
        } else if (password.length < 8){
            textInputLayout.isErrorEnabled = true
            textInputLayout.error = getString(R.string.text_error_password_less_than_8_char)
            false
        } else{
            textInputLayout.isErrorEnabled = false
            true
        }
    }

    private fun checkPwdAndConfirmPwd(password: String, confirmPassword: String): Boolean {
        //todo : check form validation
        return if (password != confirmPassword){
            binding.layoutForm.tilPassword.isErrorEnabled = true
            binding.layoutForm.tilConfirmPassword.isErrorEnabled = true
            binding.layoutForm.tilPassword.error = getString(R.string.text_password_does_not_match)
            binding.layoutForm.tilConfirmPassword.error = getString(R.string.text_password_does_not_match)
            false
        } else{
            binding.layoutForm.tilPassword.isErrorEnabled = false
            binding.layoutForm.tilConfirmPassword.isErrorEnabled = false
            true
        }
    }
}