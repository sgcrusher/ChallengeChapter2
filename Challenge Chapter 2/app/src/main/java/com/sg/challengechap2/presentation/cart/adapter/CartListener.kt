package com.sg.challengechap2.presentation.cart.adapter

import com.sg.challengechap2.model.Cart

interface CartListener {
    // fun onCartClicked(item: Cart)
    fun onPlusTotalItemCartClicked(cart: Cart)
    fun onMinusTotalItemCartClicked(cart: Cart)
    fun onRemoveCartClicked(cart: Cart)
    fun onUserDoneEditingNotes(cart: Cart)
}
