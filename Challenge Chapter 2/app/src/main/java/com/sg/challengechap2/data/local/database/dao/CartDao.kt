package com.sg.challengechap2.data.local.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.sg.challengechap2.data.local.database.entity.CartEntity
import com.sg.challengechap2.data.local.database.relation.CartFoodRelation
import kotlinx.coroutines.flow.Flow

@Dao
interface CartDao {

    @Query("SELECT * FROM CARTS")
    fun getAllCarts() : Flow<List<CartFoodRelation>>

    @Query("SELECT * FROM CARTS WHERE cartId == :cartId")
    fun getCartById(cartId: Int) : Flow<CartFoodRelation>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCarts(carts: List<CartEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCart(cart: CartEntity) : Long

    @Update
    suspend fun updateCart(cart: CartEntity) : Int

    @Delete
    suspend fun deleteCart(cart: CartEntity) : Int

    @Query("DELETE FROM CARTS")
    suspend fun deleteAllCarts() : Int
}