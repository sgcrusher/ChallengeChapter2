package com.sg.challengechap2.presentation.profile

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import coil.load
import coil.transform.CircleCropTransformation
import com.google.firebase.auth.FirebaseAuth
import com.sg.challengechap2.R
import com.sg.challengechap2.data.network.firebase.auth.FirebaseAuthDataSource
import com.sg.challengechap2.data.network.firebase.auth.FirebaseAuthDataSourceImpl
import com.sg.challengechap2.data.repository.UserRepositoryImpl
import com.sg.challengechap2.databinding.FragmentProfileBinding
import com.sg.challengechap2.presentation.login.LoginActivity
import com.sg.challengechap2.utils.GenericViewModelFactory
import com.sg.challengechap2.utils.proceedWhen
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf


class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding


    private val viewModel: ProfileViewModel by viewModel()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupForm()
        showUserData()
        setClickListeners()
        observeData()
    }

    private fun observeData() {
        viewModel.changePhotoResult.observe(viewLifecycleOwner) {
            it.proceedWhen(doOnSuccess = {
                Toast.makeText(requireContext(), "Change Photo Profile Success !", Toast.LENGTH_SHORT).show()
                showUserData()
            }, doOnError = {
                Toast.makeText(requireContext(), "Change Photo Profile Failed !", Toast.LENGTH_SHORT).show()
                showUserData()
            })
        }
        viewModel.changeProfileResult.observe(viewLifecycleOwner) {
            it.proceedWhen(
                doOnSuccess = {
                    binding.pbLoading.isVisible = false
                    binding.btnChangeProfile.isVisible = true
                    Toast.makeText(requireContext(), "Change Profile data Success !", Toast.LENGTH_SHORT).show()
                },
                doOnError = {
                    binding.pbLoading.isVisible = false
                    binding.btnChangeProfile.isVisible = true
                    Toast.makeText(requireContext(), "Change Profile data Failed !", Toast.LENGTH_SHORT).show()

                },
                doOnLoading = {
                    binding.pbLoading.isVisible = true
                    binding.btnChangeProfile.isVisible = false
                }
            )
        }
    }

    private fun setClickListeners() {
        binding.btnChangeProfile.setOnClickListener {
            if (checkNameValidation()) {
                changeProfileData()
            }
        }
        binding.ivEditPhoto.setOnClickListener {
            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }
        binding.tvChangePwd.setOnClickListener {
            requestChangePassword()
        }
        binding.tvLogout.setOnClickListener {
            doLogout()
        }
    }

    private fun doLogout() {
        val dialog = AlertDialog.Builder(requireContext()).setMessage("Do you want to logout ?")
            .setPositiveButton(
                "Yes"
            ) { dialog, id ->
                viewModel.doLogout()
                navigateToLogin()
            }
            .setNegativeButton(
                "No"
            ) { dialog, id ->
                //no-op , do nothing
            }.create()
        dialog.show()
    }

    private fun navigateToLogin() {
        requireActivity().run {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }

    private fun requestChangePassword() {
        viewModel.createChangePwdRequest()
        AlertDialog.Builder(requireContext())
            .setMessage(
                "Permintaan ubah password akan dikirim ke email: " +
                        "${viewModel.getCurrentUser()?.email}"
            )
            .setPositiveButton("Ok") { _, _ ->

            }.create().show()
    }

    private fun changeProfileData() {
        val fullName = binding.layoutForm.etName.text.toString().trim()
        viewModel.updateFullName(fullName)
    }


    private fun checkNameValidation(): Boolean {
        val fullName = binding.layoutForm.etName.text.toString().trim()
        return if (fullName.isEmpty()) {
            binding.layoutForm.tiName.isErrorEnabled = true
            binding.layoutForm.tiName.error = getString(R.string.text_error_name_cannot_empty)
            false
        } else {
            binding.layoutForm.tiName.isErrorEnabled = false
            true
        }
    }

    private fun showUserData() {
        viewModel.getCurrentUser()?.let {
            binding.layoutForm.etName.setText(it.fullName)
            binding.layoutForm.etEmail.setText(it.email)
            binding.ivProfilePict.load(it.photoUrl) {
                crossfade(true)
                placeholder(R.drawable.img_logo)
                error(R.drawable.img_logo)
                transformations(CircleCropTransformation())
            }

        }
    }

    private fun setupForm() {
        binding.layoutForm.tiName.isVisible = true
        binding.layoutForm.tiEmail.isVisible = true
        binding.layoutForm.etEmail.isEnabled = false
    }
}
