package com.sg.challengechap2.data.network.api.model.food


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

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