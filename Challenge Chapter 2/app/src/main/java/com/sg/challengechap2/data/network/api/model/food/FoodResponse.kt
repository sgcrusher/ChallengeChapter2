package com.sg.challengechap2.data.network.api.model.food

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class FoodResponse(
    @SerializedName("code")
    val code: Int?,
    @SerializedName("data")
    val data: List<FoodItemResponse>?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("status")
    val status: Boolean?
)
