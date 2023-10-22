package com.sg.challengechap2.data.local.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "carts")
data class CartEntity(
    @PrimaryKey(autoGenerate = true)
    var cartId : Int? = null,
    @ColumnInfo(name = "food_id")
    var foodId : Int = 0,
    @ColumnInfo(name = "food_name")
    val foodName: String,
    @ColumnInfo(name = "food_price")
    val foodPrice: Double,
    @ColumnInfo(name = "food_img_url")
    val foodImgUrl: String,
    @ColumnInfo(name = "item_quantity")
    var itemQuantity : Int = 0,
    @ColumnInfo(name = "item_notes")
    var itemNotes : String? = null

)