package com.sg.challengechap2.presentation.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.sg.challengechap2.data.repository.CartRepository
import com.sg.challengechap2.model.Cart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.collect


class CartViewModel(private val repo : CartRepository) : ViewModel() {

    val cartList = repo.getCartList().asLiveData(Dispatchers.IO)

    fun decreaseCart(item: Cart) {
        viewModelScope.launch {repo.decreaseCart(item).collect()}
    }

    fun increaseCart(item: Cart) {
        viewModelScope.launch {repo.increaseCart(item).collect()}
    }

    fun deleteCart(item: Cart) {
        viewModelScope.launch {repo.deleteCart(item).collect()}
    }

    fun updateNotes(item: Cart) {
        viewModelScope.launch {repo.setCartNotes(item).collect()}
    }
}