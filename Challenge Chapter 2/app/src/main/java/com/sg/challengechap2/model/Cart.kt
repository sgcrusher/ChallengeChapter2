package com.sg.challengechap2.model

data class Cart(
    var id: Int = 0,
    var foodId : Int = 0,
    var itemQuantity : Int = 0,
    var itemNotes : String? = null
)
