package com.sg.challengechap2.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.sg.challengechap2.data.repository.FoodRepository
import com.sg.challengechap2.model.Food
import com.sg.challengechap2.presentation.utils.ResultWrapper
import kotlinx.coroutines.Dispatchers

class FoodViewModel(private val repo : FoodRepository) : ViewModel(){

    val foodData : LiveData<ResultWrapper<List<Food>>>
        get() = repo.getFoods().asLiveData(Dispatchers.IO)
}
