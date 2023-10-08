package com.sg.challengechap2.data.local.database.mapper

import com.sg.challengechap2.data.local.database.entity.FoodEntity
import com.sg.challengechap2.model.Food

fun FoodEntity?.toFood() = Food(
    foodId = this?.id ?: 0,
    foodName = this?.foodName.orEmpty(),
    foodDescription = this?.foodDescription.orEmpty(),
    foodPrice = this?.foodPrice ?: 0.0,
    foodImg = this?.foodImg ?: 0,
    foodShopDistance = this?.foodShopDistance ?: 0.0,
    foodShopLocation =  this?.foodShopLocation.orEmpty(),
    foodUrllocation = this?.foodUrllocation.orEmpty()
)

fun Food?.toFoodEntity() = FoodEntity(
    id = this?.foodId ?: 0,
    foodName = this?.foodName.orEmpty(),
    foodDescription = this?.foodDescription.orEmpty(),
    foodPrice = this?.foodPrice ?: 0.0,
    foodImg = this?.foodImg ?: 0,
    foodShopDistance = this?.foodShopDistance ?: 0.0,
    foodShopLocation =  this?.foodShopLocation.orEmpty(),
    foodUrllocation = this?.foodUrllocation.orEmpty()
). apply {
    this@toFoodEntity?.foodId?.let {
        this.id = this@toFoodEntity.foodId
    }
}

fun List<FoodEntity?>.toFoodList() = this.map { it.toFood() }
fun List<Food?>.toFoodEntity() = this.map { it.toFoodEntity() }