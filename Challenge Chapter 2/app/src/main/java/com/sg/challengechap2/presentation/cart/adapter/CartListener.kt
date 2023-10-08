package com.sg.challengechap2.presentation.cart.adapter

import com.sg.challengechap2.model.Cart
import com.sg.challengechap2.model.CartFood

interface CartListener {
    fun onCartClicked(item: CartFood)
    fun onPlusTotalItemCartClicked(cart: Cart)
    fun onMinusTotalItemCartClicked(cart: Cart)
    fun onRemoveCartClicked(cart: Cart)
    fun onUserDoneEditingNotes(cart: Cart)
}