package com.sg.challengechap2.data.network.api.model.food

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import com.sg.challengechap2.model.Food

@Keep
data class FoodItemResponse(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("alamat_resto")
    val restoAddress: String?,
    @SerializedName("detail")
    val detail: String?,
    @SerializedName("harga")
    val price: Double?,
    @SerializedName("harga_format")
    val formattedPrice: String?,
    @SerializedName("image_url")
    val imageUrl: String?,
    @SerializedName("nama")
    val name: String?
)

fun FoodItemResponse.toFood() = Food(
    foodId = this.id ?: 0,
    foodPrice = this.price ?: 0.0,
    foodName = this.name.orEmpty(),
    foodFormattedPrice = this.formattedPrice.orEmpty(),
    foodDescription = this.detail.orEmpty(),
    foodShopLocation = this.restoAddress.orEmpty(),
    foodImgUrl = this.imageUrl.orEmpty()
)

fun Collection<FoodItemResponse>.toFoodList() = this.map {
    it.toFood()
}
