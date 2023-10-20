package com.sg.challengechap2.data.network.api.model


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class FoodResponse(
    @SerializedName("code")
    val code: Int?,
    @SerializedName("data")
    val data: List<Food>?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("status")
    val status: Boolean?
)