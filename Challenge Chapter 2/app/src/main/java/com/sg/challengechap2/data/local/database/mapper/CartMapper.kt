package com.sg.challengechap2.data.local.database.mapper

import com.sg.challengechap2.data.local.database.entity.CartEntity
import com.sg.challengechap2.model.Cart

fun CartEntity?.toCart() = Cart(
    id = this?.cartId ?: 0,
    foodId = this?.foodId ?: 0,
    itemQuantity = this?.itemQuantity ?: 0,
    itemNotes = this?.itemNotes.orEmpty(),
    foodPrice = this?.foodPrice ?: 0.0,
    foodName = this?.foodName.orEmpty(),
    foodImgUrl = this?.foodImgUrl.orEmpty()

)

fun Cart?.toCartEntity() = CartEntity(
    cartId = this?.id,
    foodId = this?.foodId ?: 0,
    itemQuantity = this?.itemQuantity ?: 0,
    itemNotes = this?.itemNotes.orEmpty(),
    foodPrice = this?.foodPrice ?: 0.0,
    foodName = this?.foodName.orEmpty(),
    foodImgUrl = this?.foodImgUrl.orEmpty()
)

fun List<CartEntity?>.toCartList() = this.map { it.toCart() }
