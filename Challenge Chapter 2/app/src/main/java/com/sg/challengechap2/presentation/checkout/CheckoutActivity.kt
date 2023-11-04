package com.sg.challengechap2.presentation.checkout

import CheckoutViewHolder
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.isVisible
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.sg.challengechap2.R
import com.sg.challengechap2.data.local.database.AppDatabase
import com.sg.challengechap2.data.local.database.datasource.CartDataSource
import com.sg.challengechap2.data.local.database.datasource.CartDatabaseDataSource
import com.sg.challengechap2.data.network.api.datasource.RestaurantApiDataSourceImpl
import com.sg.challengechap2.data.network.api.service.RestaurantService
import com.sg.challengechap2.data.repository.CartRepository
import com.sg.challengechap2.data.repository.CartRepositoryImpl
import com.sg.challengechap2.databinding.ActivityCheckoutBinding
import com.sg.challengechap2.presentation.cart.CartFragment
import com.sg.challengechap2.presentation.cart.adapter.CartListAdapter
import com.sg.challengechap2.utils.GenericViewModelFactory
import com.sg.challengechap2.utils.proceedWhen
import org.koin.androidx.viewmodel.ext.android.viewModel

class CheckoutActivity : AppCompatActivity() {

    private val viewModel:ChecktViewModel by viewModel()

    private val binding: ActivityCheckoutBinding by lazy {
        ActivityCheckoutBinding.inflate(
            layoutInflater
        )
    }

    private val adapter: CartListAdapter by lazy {
        CartListAdapter()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        observeData()
        setupList()
        checkoutClickListener()
        btnBackClickListener()
        observeOrderResult()
    }
    private fun observeData() {
        viewModel.cartList.observe(this) {
            it.proceedWhen(
                doOnSuccess = { result ->
                    binding.layoutState.root.isVisible =
                        false
                    binding.layoutState.pbLoading.isVisible =
                        false
                    binding.layoutState.tvError.isVisible =
                        false
                    binding.svCheckoutController.isVisible =
                        true
                    result.payload?.let { (carts, totalPrice) ->
                        adapter.setData(carts)
                        binding.totalPembayaran.text =
                            String.format(
                                "Rp. %,.0f",
                                totalPrice
                            )
                    }
                },
                doOnLoading = {
                    binding.layoutState.root.isVisible =
                        true
                    binding.layoutState.pbLoading.isVisible =
                        true
                    binding.layoutState.tvError.isVisible =
                        false
                    binding.svCheckoutController.isVisible =
                        false
                },
                doOnEmpty = { data ->
                    binding.layoutState.root.isVisible =
                        true
                    binding.layoutState.pbLoading.isVisible =
                        false
                    binding.layoutState.tvError.isVisible =
                        true
                    binding.layoutState.tvError.text =
                        getString(R.string.no_value)
                    binding.btnOrder.isClickable =
                        false
                },
                doOnError = { err ->
                    binding.layoutState.root.isVisible =
                        true
                    binding.layoutState.pbLoading.isVisible =
                        false
                    binding.layoutState.tvError.isVisible =
                        true
                    binding.layoutState.tvError.text =
                        err.exception?.message.orEmpty()
                    binding.svCheckoutController.isVisible =
                        false
                    binding.btnOrder.isClickable =
                        false
                }
            )
        }
    }
    private fun setupList() {
        binding.rvCheckout.adapter = adapter
    }
    private fun deleteAllCart() {
        viewModel.deleteAll()
    }

    private fun checkoutClickListener() {
        binding.btnOrder.setOnClickListener {
            viewModel.createOrder()
        }
    }



    private fun btnBackClickListener() {
        binding.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    private fun observeOrderResult() {
        viewModel.orderResult.observe(this) {
            it.proceedWhen(
                doOnSuccess = {
                    Toast.makeText(this, "Succes Checkout", Toast.LENGTH_SHORT).show()
                    deleteAllCart()
                    binding.layoutState.pbLoading.isVisible = false
                    binding.layoutState.tvError.isVisible = false
                },
                doOnError = {
                    Toast.makeText(
                        this,
                        "Checkout not succes",
                        Toast.LENGTH_SHORT
                    ).show()
                    binding.layoutState.pbLoading.isVisible = true
                    binding.layoutState.tvError.isVisible = false
                },
                doOnLoading = {
                    binding.layoutState.pbLoading.isVisible = true
                    binding.layoutState.tvError.isVisible = false
                    binding.layoutState.root.isVisible = true
                    binding.svCheckoutController.isVisible = false
                }
            )
        }
    }
}