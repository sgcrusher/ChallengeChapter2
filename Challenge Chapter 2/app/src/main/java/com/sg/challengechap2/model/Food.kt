package com.sg.challengechap2.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.UUID

@Parcelize
data class Food(
    val foodId: Int?,
    val foodName: String,
    val foodDescription: String,
    val foodPrice: Double,
    val foodImgUrl: String,
    val foodFormattedPrice: String,
    val foodShopLocation: String,
   // val foodUrllocation: String
) : Parcelable