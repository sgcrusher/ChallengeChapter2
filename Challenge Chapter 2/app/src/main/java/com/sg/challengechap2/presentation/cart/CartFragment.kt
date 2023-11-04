package com.sg.challengechap2.presentation.cart

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.sg.challengechap2.R
import com.sg.challengechap2.data.local.database.AppDatabase
import com.sg.challengechap2.data.local.database.datasource.CartDataSource
import com.sg.challengechap2.data.local.database.datasource.CartDatabaseDataSource
import com.sg.challengechap2.data.network.api.datasource.RestaurantApiDataSourceImpl
import com.sg.challengechap2.data.network.api.service.RestaurantService
import com.sg.challengechap2.data.repository.CartRepository
import com.sg.challengechap2.data.repository.CartRepositoryImpl
import com.sg.challengechap2.databinding.FragmentCartBinding
import com.sg.challengechap2.model.Cart
import com.sg.challengechap2.presentation.cart.adapter.CartListAdapter
import com.sg.challengechap2.presentation.cart.adapter.CartListener
import com.sg.challengechap2.presentation.checkout.CheckoutActivity
import com.sg.challengechap2.utils.GenericViewModelFactory
import com.sg.challengechap2.utils.proceedWhen
import org.koin.androidx.viewmodel.ext.android.viewModel


class CartFragment : Fragment() {

    private lateinit var binding: FragmentCartBinding

    private val cartListAdapter : CartListAdapter by lazy {
        CartListAdapter(object : CartListener{
           /* override fun onCartClicked(cart: Cart) {

            }*/

            override fun onPlusTotalItemCartClicked(cart: Cart) {
                viewModel.increaseCart(cart)
            }

            override fun onMinusTotalItemCartClicked(cart: Cart) {
                viewModel.decreaseCart(cart)
            }

            override fun onRemoveCartClicked(cart: Cart) {
                viewModel.deleteCart(cart)
            }

            override fun onUserDoneEditingNotes(newCart: Cart) {
                viewModel.updateNotes(newCart)
            }

        })
    }
    private val viewModel : CartViewModel by viewModel()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCartBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupCartList()
        observeData()
        setClickListener()
    }

    private fun setClickListener() {
        binding.btnCheckout.setOnClickListener {
            context?.startActivity(Intent(requireContext(), CheckoutActivity::class.java))
        }
    }

    private fun observeData() {
        viewModel.cartList.observe(
            viewLifecycleOwner
        ) {
            it.proceedWhen(
                doOnSuccess = { result ->
                    binding.layoutState.root.isVisible =
                        false
                    binding.layoutState.pbLoading.isVisible =
                        false
                    binding.layoutState.tvError.isVisible =
                        false
                    binding.rvCartList.isVisible =
                        true
                    result.payload?.let { (carts, totalPrice) ->
                        cartListAdapter.setData(
                            carts
                        )
                        binding.tvTotalPriceAmount.text =
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
                    binding.rvCartList.isVisible =
                        false
                },
                doOnEmpty = { result ->
                    binding.layoutState.root.isVisible =
                        true
                    binding.rvCartList.isVisible =
                        false
                    binding.layoutState.pbLoading.isVisible =
                        false
                    binding.layoutState.tvError.isVisible =
                        true
                    binding.layoutState.tvError.text =
                        getString(R.string.no_value)
                    result.payload?.let { (_, totalPrice) ->
                        binding.tvTotalPriceAmount.text =
                            String.format(
                                "Rp. ",
                                totalPrice
                            )
                    }
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
                    binding.rvCartList.isVisible =
                        false
                }
            )
        }
    }

    private fun setupCartList() {
        binding.rvCartList.adapter = cartListAdapter
    }
}