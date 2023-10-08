package com.sg.challengechap2.data.local.database.datasource

import com.sg.challengechap2.data.local.database.dao.FoodDao
import com.sg.challengechap2.data.local.database.entity.FoodEntity
import kotlinx.coroutines.flow.Flow

interface FoodDataSource {

    fun getAllFoods(): Flow<List<FoodEntity>>

    fun getFoodById(id: Int): Flow<FoodEntity>

    suspend fun insertFoods(foods: List<FoodEntity>)

    suspend fun updateFood(food: FoodEntity): Int

    suspend fun deletefood(food: FoodEntity): Int
}

class FoodDatabaseDataSource(private val dao: FoodDao) : FoodDataSource {
    override fun getAllFoods(): Flow<List<FoodEntity>> {
        return dao.getAllFoods()
    }

    override fun getFoodById(id: Int): Flow<FoodEntity> {
        return dao.getFoodById(id)
    }

    override suspend fun insertFoods(foods: List<FoodEntity>) {
return dao.insertFoods(foods)    }

    override suspend fun updateFood(food: FoodEntity): Int {
        return dao.updateFood(food)
    }

    override suspend fun deletefood(food: FoodEntity): Int {
        return dao.deleteFood(food)
    }

}