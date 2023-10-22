package com.sg.challengechap2.data.network.api.service


import com.sg.challengechap2.BuildConfig
import com.sg.challengechap2.data.network.api.model.category.CategoryResponse
import com.sg.challengechap2.data.network.api.model.food.FoodResponse
import com.sg.challengechap2.data.network.api.model.order.OrderRequest
import com.sg.challengechap2.data.network.api.model.order.OrdersResponse
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

interface RestaurantService {

    @GET("listmenu")
    suspend fun getFoods(@Query("c") category: String? = null): FoodResponse

    @GET("category")
    suspend fun getCategories(): CategoryResponse

    @POST("order")
    suspend fun createOrder(@Body orderRequest: OrderRequest): OrdersResponse
    companion object {
        @JvmStatic
        operator fun invoke(): RestaurantService {
            val okHttpClient = OkHttpClient.Builder()
                .connectTimeout(120, TimeUnit.SECONDS)
                .readTimeout(120, TimeUnit.SECONDS)
                .build()
            val retrofit = Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()
            return retrofit.create(RestaurantService::class.java)
        }
    }
}