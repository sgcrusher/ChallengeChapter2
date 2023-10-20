package com.sg.challengechap2.data.network.api.model


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class Food(
    @SerializedName("alamat_resto")
    val restoAddress: String?,
    @SerializedName("detail")
    val detail: String?,
    @SerializedName("harga")
    val Price: Int?,
    @SerializedName("harga_format")
    val formattedPrice: String?,
    @SerializedName("image_url")
    val imageUrl: String?,
    @SerializedName("nama")
    val name: String?
)