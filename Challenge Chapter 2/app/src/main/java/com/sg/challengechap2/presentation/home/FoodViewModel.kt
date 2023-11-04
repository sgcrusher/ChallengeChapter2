package com.sg.challengechap2.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sg.challengechap2.data.repository.FoodRepository
import com.sg.challengechap2.data.repository.UserRepository
import com.sg.challengechap2.model.CategoryFood
import com.sg.challengechap2.model.Food
import com.sg.challengechap2.utils.ResultWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class FoodViewModel(
    private val repo: FoodRepository,
    private val userRepo: UserRepository
) : ViewModel() {

    private val _categories = MutableLiveData<ResultWrapper<List<CategoryFood>>>()

    val categories: LiveData<ResultWrapper<List<CategoryFood>>>
        get() = _categories

    private val _foodData = MutableLiveData<ResultWrapper<List<Food>>>()
    val foodData: LiveData<ResultWrapper<List<Food>>>
        get() = _foodData

    fun getCurrentUser() = userRepo.getCurrentUser()

    fun getCategories() {
        viewModelScope.launch(Dispatchers.IO) {
            repo.getCategories().collect {
                _categories.postValue(it)
            }
        }
    }

    fun getFoods(categoryFood: String? = null) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.getFoods(if (categoryFood == "all")null else categoryFood).collect {
                _foodData.postValue(it)
            }
        }
    }
}
