package com.sg.challengechap2.data.local.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.sg.challengechap2.data.local.database.entity.FoodEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FoodDao{
    @Query("SELECT * FROM FOODS")
    fun getAllFoods() : Flow<List<FoodEntity>>

    @Query("SELECT * FROM FOODS WHERE id == :id")
    fun getFoodById(id: Int) : Flow<FoodEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFoods(foods: List<FoodEntity>)

    @Update
    suspend fun updateFood(food: FoodEntity) : Int

    @Delete
    suspend fun deleteFood(food: FoodEntity) : Int

}
