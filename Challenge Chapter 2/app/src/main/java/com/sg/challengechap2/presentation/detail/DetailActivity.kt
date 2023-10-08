package com.sg.challengechap2.presentation.detail

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import coil.load
import com.sg.challengechap2.R
import com.sg.challengechap2.data.local.database.AppDatabase
import com.sg.challengechap2.data.local.database.datasource.CartDataSource
import com.sg.challengechap2.data.local.database.datasource.CartDatabaseDataSource
import com.sg.challengechap2.data.repository.CartRepository
import com.sg.challengechap2.data.repository.CartRepositoryImpl
import com.sg.challengechap2.databinding.ActivityDetailBinding
import com.sg.challengechap2.model.Food
import com.sg.challengechap2.utils.GenericViewModelFactory
import com.sg.challengechap2.utils.proceedWhen

class DetailActivity : AppCompatActivity() {

    private val binding : ActivityDetailBinding by lazy {
        ActivityDetailBinding.inflate(layoutInflater)
    }

    private val viewModel: DetailViewModel by viewModels {
        val database = AppDatabase.getInstance(this)
        val cartDao = database.cartDao()
        val cartDataSource: CartDataSource = CartDatabaseDataSource(cartDao)
        val repo: CartRepository = CartRepositoryImpl(cartDataSource)
        GenericViewModelFactory.create(DetailViewModel(intent?.extras, repo)
        )
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        showFoodData(viewModel.food )
        observeData()
        backToHomeClickListener()
        countingClickListener()
        mapsClickListener()

    }

    private fun observeData() {
        viewModel.priceLiveData.observe(this){
            binding.buttonAddToChart.text =  getString(R.string.tv_add_to_chart)
            //binding.priceTotal.text
        }

        viewModel.foodCountLiveData.observe(this){
            binding.tvFoodTotal.text = it.toString()
        }

        viewModel.addToCartResult.observe(this) {
            it.proceedWhen(
                doOnSuccess = {
                    Toast.makeText(this, "Add to cart success !", Toast.LENGTH_SHORT).show()
                    finish()
                }, doOnError = {
                    Toast.makeText(this, it.exception?.message.orEmpty(), Toast.LENGTH_SHORT).show()
                })
        }
    }
    private fun backToHomeClickListener() {
        binding.ivBackDetail.setOnClickListener {
            onBackPressed()
        }
    }

    private fun countingClickListener() {
        binding.ivPlusButton.setOnClickListener {
            viewModel.add()
        }
        binding.ivMinusButton.setOnClickListener {
            viewModel.minus()
        }
        binding.buttonAddToChart.setOnClickListener{
            viewModel.addToCart()
        }
    }

    private fun showFoodData(food: Food?) {
        food?.let {
            binding.ivFoodDetailImg.load(it.foodImg)
            binding.tvFoodNameDetail.text = it.foodName
            binding.tvFoodPrice.text = getString(R.string.tv_food_price_format, food?.foodPrice)
            binding.tvFoodDescription.text = it.foodDescription
            binding.tvShopLocation.text = food?.foodShopLocation
            binding.tvFoodShopDistance.text = getString(R.string.tv_shop_distance_format, food?.foodShopDistance)
            binding.buttonAddToChart.text = getString(R.string.tv_add_to_chart)
        }
    }

    private fun mapsClickListener() {
        binding.llShopLocation.setOnClickListener {
            navigateToGoogleMaps()
        }
    }

    private fun navigateToGoogleMaps() {
        val mapsIntentUri =
            Uri.parse("https://maps.app.goo.gl/QLChXJcYJUuQWPQh8")
        val mapsIntent = Intent(
            Intent.ACTION_VIEW,
            mapsIntentUri
        )
        startActivity(mapsIntent)
    }

    companion object {
        const val EXTRA_PRODUCT = "EXTRA_PRODUCT"
        fun startActivity(context: Context, food: Food?) {
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra(EXTRA_PRODUCT, food)
            context.startActivity(intent)
        }
    }
}