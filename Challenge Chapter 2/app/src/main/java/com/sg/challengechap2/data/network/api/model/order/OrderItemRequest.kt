package com.sg.challengechap2.data.network.api.model.order

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
class OrderItemRequest (
    @SerializedName("catatan")
    val notes: String?,
    @SerializedName("harga")
    val price: Int?,
    @SerializedName("nama")
    val name: String?,
    @SerializedName("qty")
    val qty: Int?
)