package com.sg.challengechap2.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.UUID

@Parcelize
data class Food(
    val foodId: Int? = null,
    val foodName: String,
    val foodDescription: String,
    val foodPrice: Double,
    val foodImg: Int,
    val foodShopDistance: Double,
    val foodShopLocation: String,
    val foodUrllocation: String
) : Parcelable