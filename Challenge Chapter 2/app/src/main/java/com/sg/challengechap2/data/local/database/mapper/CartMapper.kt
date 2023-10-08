package com.sg.challengechap2.data.local.database.mapper

import com.sg.challengechap2.data.local.database.entity.CartEntity
import com.sg.challengechap2.data.local.database.relation.CartFoodRelation
import com.sg.challengechap2.model.Cart
import com.sg.challengechap2.model.CartFood

fun CartEntity?.toCart() = Cart(
    id = this?.cartId ?: 0,
    foodId = this?.foodId ?: 0,
    itemQuantity = this?.itemQuantity ?: 0,
    itemNotes = this?.itemNotes.orEmpty()
)

fun Cart?.toCartEntity() = CartEntity(
    cartId = this?.id,
    foodId = this?.foodId ?: 0,
    itemQuantity = this?.itemQuantity ?: 0,
    itemNotes = this?.itemNotes.orEmpty()
)

fun CartFoodRelation.toCartFood() = CartFood(
    cart = this.cart.toCart(),
    food = this.food.toFood()
)

fun List<CartEntity?>.toCartList() = this.map { it.toCart() }

fun List<CartFoodRelation>.toCartFoodList() = this.map { it.toCartFood() }