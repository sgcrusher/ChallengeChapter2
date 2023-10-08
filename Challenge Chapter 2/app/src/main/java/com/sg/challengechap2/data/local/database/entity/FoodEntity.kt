package com.sg.challengechap2.data.local.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID


@Entity(tableName = "foods")
data class FoodEntity(

    @PrimaryKey(autoGenerate = true)
    var id : Int? = null,
    @ColumnInfo(name = "food_name")
    val foodName: String,
    @ColumnInfo(name = "food_description")
    val foodDescription : String,
    @ColumnInfo(name = "food_price")
    val foodPrice: Double,
    @ColumnInfo(name = "food_img")
    val foodImg: Int,
    @ColumnInfo(name = "food_shop_distance")
    val foodShopDistance: Double,
    @ColumnInfo(name = "food_shop_location")
    val foodShopLocation: String,
    @ColumnInfo(name = "food_url_location")
    val foodUrllocation: String

)