package com.sg.challengechap2.di

import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.google.firebase.auth.FirebaseAuth
import com.sg.challengechap2.data.local.database.AppDatabase
import com.sg.challengechap2.data.local.database.datasource.CartDataSource
import com.sg.challengechap2.data.local.database.datasource.CartDatabaseDataSource
import com.sg.challengechap2.data.local.datastore.UserPreferenceDataSource
import com.sg.challengechap2.data.local.datastore.UserPreferenceDataSourceImpl
import com.sg.challengechap2.data.local.datastore.appDataStore
import com.sg.challengechap2.data.network.api.datasource.RestaurantApiDataSource
import com.sg.challengechap2.data.network.api.datasource.RestaurantApiDataSourceImpl
import com.sg.challengechap2.data.network.api.service.RestaurantService
import com.sg.challengechap2.data.network.firebase.auth.FirebaseAuthDataSource
import com.sg.challengechap2.data.network.firebase.auth.FirebaseAuthDataSourceImpl
import com.sg.challengechap2.data.repository.CartRepository
import com.sg.challengechap2.data.repository.CartRepositoryImpl
import com.sg.challengechap2.data.repository.FoodRepository
import com.sg.challengechap2.data.repository.FoodRepositoryImpl
import com.sg.challengechap2.data.repository.UserRepository
import com.sg.challengechap2.data.repository.UserRepositoryImpl
import com.sg.challengechap2.presentation.cart.CartViewModel
import com.sg.challengechap2.presentation.checkout.ChecktViewModel
import com.sg.challengechap2.presentation.detail.DetailViewModel
import com.sg.challengechap2.presentation.home.FoodViewModel
import com.sg.challengechap2.presentation.login.LoginViewModel
import com.sg.challengechap2.presentation.main.MainViewModel
import com.sg.challengechap2.presentation.profile.ProfileViewModel
import com.sg.challengechap2.presentation.register.RegisterViewModel
import com.sg.challengechap2.presentation.splashscreen.SplashScreenViewModel
import com.sg.challengechap2.utils.PreferenceDataStoreHelper
import com.sg.challengechap2.utils.PreferenceDataStoreHelperImpl
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.Module
import org.koin.dsl.module

object AppModules {

    private val localModule = module {
        single { AppDatabase.getInstance(androidContext()) }
        single { get<AppDatabase>().cartDao() }
        single { androidContext().appDataStore }
        single<PreferenceDataStoreHelper> { PreferenceDataStoreHelperImpl(get()) }
    }
    private val networkModule = module {
        single { ChuckerInterceptor(androidContext()) }
        single { RestaurantService.invoke(get()) }
        single { FirebaseAuth.getInstance() }
    }

    private val dataSourceModule = module {
        single<CartDataSource> { CartDatabaseDataSource(get()) }
        single<RestaurantApiDataSource> { RestaurantApiDataSourceImpl(get()) }
        single<UserPreferenceDataSource> { UserPreferenceDataSourceImpl(get()) }
        single<FirebaseAuthDataSource> { FirebaseAuthDataSourceImpl(get()) }
    }

    private val repositoryModule = module {
        single<CartRepository> { CartRepositoryImpl(get(), get()) }
        single<FoodRepository> { FoodRepositoryImpl(get()) }
        single<UserRepository> { UserRepositoryImpl(get()) }
    }

    private val viewModelModule = module {
        viewModelOf(::LoginViewModel)
        viewModelOf(::RegisterViewModel)
        viewModelOf(::ChecktViewModel)
        viewModelOf(::CartViewModel)
        viewModelOf(::FoodViewModel)
        viewModelOf(::MainViewModel)
        viewModelOf(::ProfileViewModel)
        viewModelOf(::SplashScreenViewModel)
        viewModel { DetailViewModel(get(), get()) }
    }

    val modules: List<Module> = listOf(
        localModule,
        networkModule,
        dataSourceModule,
        repositoryModule,
        viewModelModule
    )
}
