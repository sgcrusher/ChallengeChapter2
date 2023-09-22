package com.sg.challengechap2.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CategoryFood(
    val categoryImg: Int,
    val categoryName: String
) : Parcelable
