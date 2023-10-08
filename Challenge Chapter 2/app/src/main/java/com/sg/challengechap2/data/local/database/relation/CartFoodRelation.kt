package com.sg.challengechap2.data.local.database.relation

import androidx.room.Embedded
import androidx.room.Relation
import com.sg.challengechap2.data.local.database.entity.CartEntity
import com.sg.challengechap2.data.local.database.entity.FoodEntity

data class CartFoodRelation (
    @Embedded
    val cart: CartEntity,
    @Relation(parentColumn = "food_id", entityColumn = "id")
    val food: FoodEntity
)
