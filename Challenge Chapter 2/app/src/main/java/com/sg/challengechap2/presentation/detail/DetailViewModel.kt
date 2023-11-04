package com.sg.challengechap2.presentation.detail

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sg.challengechap2.data.repository.CartRepository
import com.sg.challengechap2.model.Food
import com.sg.challengechap2.utils.ResultWrapper
import kotlinx.coroutines.launch

class DetailViewModel(
    private val extras: Bundle?,
    private val cartRepository: CartRepository
) : ViewModel() {

    val food = extras?.getParcelable<Food>(DetailActivity.EXTRA_PRODUCT)

    val priceLiveData = MutableLiveData<Double>().apply {
        postValue(0.0)
    }
    val foodCountLiveData = MutableLiveData<Int>().apply {
        postValue(0)
    }
    private val _addToCartResult = MutableLiveData<ResultWrapper<Boolean>>()

    val addToCartResult: LiveData<ResultWrapper<Boolean>>
        get() = _addToCartResult

    fun add() {
        val count = (foodCountLiveData.value ?: 0) + 1
        foodCountLiveData.postValue(count)
        priceLiveData.postValue(food?.foodPrice?.times(count) ?: 0.0)
    }

    fun minus() {
        if ((foodCountLiveData.value ?: 0) > 0) {
            val count = (foodCountLiveData.value ?: 0) - 1
            foodCountLiveData.postValue(count)
            priceLiveData.postValue(food?.foodPrice?.times(count) ?: 0.0)
        }
    }

    fun addToCart() {
        viewModelScope.launch {
            val qty =
                if ((foodCountLiveData.value ?: 0) <= 0) 1 else foodCountLiveData.value ?: 0
            food?.let {
                cartRepository.createCart(it, qty).collect { result ->
                    _addToCartResult.postValue(result)
                }
            }
        }
    }
}
