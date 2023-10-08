package com.sg.challengechap2.data.repository

import com.sg.challengechap2.data.local.database.datasource.CartDataSource
import com.sg.challengechap2.data.local.database.entity.CartEntity
import com.sg.challengechap2.data.local.database.mapper.toCartEntity
import com.sg.challengechap2.data.local.database.mapper.toCartFoodList
import com.sg.challengechap2.model.Cart
import com.sg.challengechap2.model.CartFood
import com.sg.challengechap2.model.Food
import com.sg.challengechap2.utils.ResultWrapper
import com.sg.challengechap2.utils.proceed
import com.sg.challengechap2.utils.proceedFlow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

interface CartRepository {
    fun getCartList(): Flow<ResultWrapper<Pair<List<CartFood>, Double>>>
    suspend fun createCart(food: Food, totalQuantity: Int): Flow<ResultWrapper<Boolean>>
    suspend fun decreaseCart(item: Cart): Flow<ResultWrapper<Boolean>>
    suspend fun increaseCart(item: Cart): Flow<ResultWrapper<Boolean>>
    suspend fun setCartNotes(item: Cart): Flow<ResultWrapper<Boolean>>
    suspend fun deleteCart(item: Cart): Flow<ResultWrapper<Boolean>>
    suspend fun deleteAll()
}

class CartRepositoryImpl(
    private val dataSource: CartDataSource
) : CartRepository {
    override fun getCartList(): Flow<ResultWrapper<Pair<List<CartFood>, Double>>> {
        return dataSource.getAllCarts().map {
            proceed {
                val cartList = it.toCartFoodList()
                val totalPrice = cartList.sumOf {
                    val quantity = it.cart.itemQuantity
                    val pricePerItem = it.food.foodPrice
                    quantity * pricePerItem
                }
                Pair(cartList, totalPrice)
            }
        }.map {
            if (it.payload?.first?.isEmpty() == true)
                ResultWrapper.Empty(it.payload)
            else
                it
        }.onStart {
            emit(ResultWrapper.Loading())
            delay(2000)
        }
    }

    override suspend fun createCart(food: Food, totalQuantity: Int): Flow<ResultWrapper<Boolean>> {
        return food.foodId?.let { foodId ->
            proceedFlow {
                val affectedRow = dataSource.insertCart(
                    CartEntity(foodId = foodId, itemQuantity = totalQuantity)
                )
                affectedRow > 0
            }
        } ?: flow {
            emit(ResultWrapper.Error(IllegalStateException("Product ID not found")))
        }
    }

    override suspend fun decreaseCart(item: Cart): Flow<ResultWrapper<Boolean>> {
        val modifiedCart = item.copy().apply {
            itemQuantity -= 1
        }
        return if (modifiedCart.itemQuantity <= 0) {
            proceedFlow { dataSource.deleteCart(modifiedCart.toCartEntity()) > 0 }
        } else {
            proceedFlow { dataSource.updateCart(modifiedCart.toCartEntity()) > 0 }
        }
    }

    override suspend fun increaseCart(item: Cart): Flow<ResultWrapper<Boolean>> {
        val modifiedCart = item.copy().apply {
            itemQuantity += 1
        }
        return proceedFlow { dataSource.updateCart(modifiedCart.toCartEntity()) > 0 }
    }

    override suspend fun setCartNotes(item: Cart): Flow<ResultWrapper<Boolean>> {
        return proceedFlow { dataSource.updateCart(item.toCartEntity()) > 0 }
    }

    override suspend fun deleteCart(item: Cart): Flow<ResultWrapper<Boolean>> {
        return proceedFlow { dataSource.deleteCart(item.toCartEntity()) > 0 }
    }

    override suspend fun deleteAll() {
        dataSource.deleteAllCarts()
    }

}